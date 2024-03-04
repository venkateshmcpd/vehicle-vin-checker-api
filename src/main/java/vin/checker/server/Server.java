package vin.checker.server;

import static vin.checker.constants.VinCheckerConstants.*;
import static vin.checker.constants.VinCheckerApiConstants.*;
import static vin.checker.constants.VinCheckerConstants.CONTENT_TYPE_APPLICATION_JSON;
import static vin.checker.constants.VinCheckerConstants.ERROR_MESSAGE;
import static vin.checker.constants.VinCheckerConstants.EXCEPTION;
import static vin.checker.constants.VinCheckerConstants.LOCALE;
import static vin.checker.constants.VinCheckerConstants.LOCALE_EN;
import static vin.checker.constants.VinCheckerConstants.MESSAGE;
import static vin.checker.constants.VinCheckerConstants.OK;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Server extends AbstractVerticle {

	@Override
	public void start() throws IOException {
		log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& in Server verticle Start method &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& ");
		EventBus eb = vertx.eventBus();
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());

		// heartbeat api check
		router.route().path(API_GET_HEARTBEAT).handler(routingContext -> {
			routingContext.response().setStatusCode(200);
			routingContext.response().putHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
			routingContext.response().end(new JsonObject().put(CODE, 200).put(MESSAGE, OK).toString());
		});
		
        // extract query params
        router.route("/api/*").handler(routingContext -> {
            final Long genericTime = System.currentTimeMillis();
            String requestOrigin = routingContext.request().headers().get("origin");
            log.info("origin from headers: " + requestOrigin);
            if (requestOrigin == null) {
                requestOrigin = VinCheckerConfig.getHttpProtocol() + routingContext.request().host();
            }

            log.info("requestOrigin: " + requestOrigin);
            // For localhost testing
            routingContext.response().headers().set("Access-Control-Allow-Origin", requestOrigin);
            routingContext.response().headers().set("Access-Control-Allow-Credentials", "true");
            routingContext.response().headers().set("Access-Control-Allow-Headers", "Content-Type");

            // Getting params from the request
            JsonObject queryParams = new JsonObject();
            Iterator<Entry<String, String>> entries = routingContext.queryParams().iterator();
            while (entries.hasNext()) {
                Entry<String, String> entry = entries.next();
                queryParams.put(entry.getKey(), entry.getValue().trim());
            }

            // Need to determine the handler based on resource
            routingContext.put("api_time", genericTime);// will be passed to the worker.
            routingContext.put("query_params", queryParams);// will be passed to the worker.
            routingContext.put("request_origin", requestOrigin);

            // For Passing the context to next route
            routingContext.next();
        });

        // valid api check / authentication check / authorization check
        router.route("/api/*").blockingHandler(routingContext -> {
            String apiHandler = routingContext.request().path().toLowerCase();
            String httpMethod = routingContext.request().method().name().toLowerCase();
            apiHandler = httpMethod + "." + apiHandler.replace("/api/", "");
            if(!API_END_POINTS.contains(apiHandler)) {
            	routingContext.response().setStatusCode(404)
            	.end(new JsonObject().put(CODE, 404).put(MESSAGE, API_NOT_FOUND).encode());
            }
            routingContext.put("api_handler", apiHandler);
            // handle authentication/authorization checks here
            routingContext.next();
        });
		
		// worker(apiHandler) called here and response sent to client.
        router.route("/api/*").handler(routingContext -> {
			final Long startTime = System.currentTimeMillis();
			final Buffer body = routingContext.getBody();
			JsonObject messageInfo = new JsonObject(body.toString());
			messageInfo.put(LOCALE, LOCALE_EN);
            messageInfo.put("query_params", (JsonObject)routingContext.get(QUERY_PARAMS));

			// send to handlers
			HttpServerResponse response = routingContext.response();
			DeliveryOptions deliveryOptions = new DeliveryOptions().setSendTimeout(10000); 
			String apiHandler = routingContext.get("api_handler");
			eb.request(apiHandler, messageInfo.toString(), deliveryOptions, reply -> {
				Integer replyCode = null;
				String replyMessage = null;
				JsonObject replyBody = null;
				Long processingTime = System.currentTimeMillis() - startTime;
				if(reply.succeeded()) {
					replyBody = (JsonObject) reply.result().body();
					replyCode = replyBody.getInteger(CODE);
					replyMessage = replyBody.getString(MESSAGE);                    
				} else {
					ReplyException cause = (ReplyException) reply.cause();
					replyCode = 404;
					replyMessage = REPLY_FAILURES_MAP.get(cause.failureType());
					replyBody = new JsonObject()
							.put(CODE, replyCode)
							.put(MESSAGE, replyMessage)
							.put(ERROR_MESSAGE, replyMessage);
					log.error(EXCEPTION + reply.cause().getMessage());
				}

				log.info(String.format(RESPONSE_MESSAGES_MAP.get(replyCode), apiHandler, processingTime));
				response.setStatusCode(replyCode);
				response.setStatusMessage(replyMessage);
				response.putHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON);
				response.end(replyBody.encode());
				return;
			});
		});

		// Create the HTTP server 
		HttpServerOptions serverOptions = new HttpServerOptions().setCompressionSupported(true).setMaxInitialLineLength(200000);
		HttpServer server = vertx.createHttpServer(serverOptions);
		server.requestHandler(router).listen(VinCheckerConfig.getVinCheckerApiPort(), VinCheckerConfig.getVinCheckerApiHost());
		log.info("SERVER IS UP");
	}
}
