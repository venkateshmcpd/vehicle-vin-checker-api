package vin.checker.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public final class JsonUtils {
		
	public static String getQueryStringValue(JsonObject queryParams, String key) {
		String val = queryParams.getString(key);
		if(val != null && !val.trim().isEmpty()) {
			return val;
		}
		throw VinRequestError.invalid(key);
	}
	
	public static String getBodyStringValue(JsonObject messageBody, String key) {
		Object obj = messageBody.getValue(key);
		if(obj instanceof String) {
			String val = (String)obj;
			if(!val.trim().isEmpty()) {
				return val;
			}
		}
		throw VinRequestError.invalid(key);
	}
	
	public static Integer getBodyIntegerValue(JsonObject messageBody, String key) {
		Object obj = messageBody.getValue(key);
		if(obj instanceof Integer) {
			return (Integer)obj;
		}
		throw VinRequestError.invalid(key);
	}

	private JsonUtils() {
		
	}
}
