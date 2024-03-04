package vin.checker.service;

import static vin.checker.constants.VinCheckerConstants.BODY;
import static vin.checker.constants.VinCheckerConstants.CONTENT_TYPE;
import static vin.checker.constants.VinCheckerConstants.CONTENT_TYPE_APPLICATION_JSON;
import static vin.checker.constants.VinCheckerConstants.QUERY_PARAMS;
import static vin.checker.constants.VinCheckerConstants.PATH_SEGMENTS;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class HttpClientService {

	public static JsonObject getGetApiResponse(String apiUrl, JsonObject requestData) throws Exception {
		long startTime = System.currentTimeMillis();
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(30 * 1000);
		requestConfig.setConnectionRequestTimeout(30 * 1000);
		requestConfig.setSocketTimeout(30 * 1000);
		HttpGet httpGet = new HttpGet(apiUrl);
		List<NameValuePair> queryParams = buildUriQueryParams(requestData);
		List<String> pathSegments = buildUriPathSegments(requestData);
		if(!queryParams.isEmpty() || !pathSegments.isEmpty()) {
			URI uri = new URIBuilder(httpGet.getURI())
					.setPathSegments(pathSegments)
					.addParameters(queryParams)					
					.build();
			httpGet.setURI(uri);
		}

		httpGet.setConfig(requestConfig.build());
		httpGet.addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
		log.info(httpGet.getURI());
		try(CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse apiResponse = client.execute(httpGet);
			String json = EntityUtils.toString(apiResponse.getEntity());
			JsonObject response = new JsonObject(json);			
			log.info(response);
			log.info((System.currentTimeMillis() - startTime) + " milliseconds");
			return response;
		} 
	}	

	/**
	 * get post api response
	 * 
	 * @param requestBody
	 * @param apiUrl
	 * @param serviceName
	 * @return
	 * @throws Exception 
	 * @throws FrxRuntimeException 
	 */
	public static void getPostApiResponse(String apiUrl, JsonObject requestData, String serviceName) throws Exception {
		long startTime = System.currentTimeMillis();
		RequestConfig.Builder requestConfig = RequestConfig.custom();
		requestConfig.setConnectTimeout(30 * 1000);
		requestConfig.setConnectionRequestTimeout(30 * 1000);
		requestConfig.setSocketTimeout(30 * 1000);
		HttpPost httpPost = new HttpPost(apiUrl);
		if(requestData.containsKey(QUERY_PARAMS)) {
			List<NameValuePair> nameValuePairs = buildUriQueryParams(requestData);
			URI uri = new URIBuilder(httpPost.getURI())
					.addParameters(nameValuePairs)
					.build();
			httpPost.setURI(uri);
		}
		httpPost.setConfig(requestConfig.build());
		if(requestData.containsKey(BODY)) {
			StringEntity params = new StringEntity(requestData.getJsonObject(BODY).toString());
			httpPost.setEntity(params);
		}
		httpPost.addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
		log.info(httpPost.getURI());
		try(CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse apiResponse = client.execute(httpPost);
			String json = EntityUtils.toString(apiResponse.getEntity());
			JsonObject response = new JsonObject(json);
			log.info(response);
			if(!"200".equals(response.getString("code"))) {
				// log request error log
			}
		} 
		log.info((System.currentTimeMillis() - startTime) + " milliseconds");
		log.info(" -------------------------------------------------------------------------------------------- ");
	}
	
	private static List<NameValuePair> buildUriQueryParams(JsonObject requestData) {
		JsonObject queryParams = requestData.getJsonObject(QUERY_PARAMS);
	    List<NameValuePair> nameValuePairs = new ArrayList<>();
	    for(String fieldName : queryParams.fieldNames()) {
		    nameValuePairs.add(new BasicNameValuePair(fieldName, queryParams.getString(fieldName)));
	    }
	    return nameValuePairs;
	}

	private static List<String> buildUriPathSegments(JsonObject requestData) {
		return requestData.getJsonArray(PATH_SEGMENTS).getList();
	}
	
	private HttpClientService() {
		
	}
}