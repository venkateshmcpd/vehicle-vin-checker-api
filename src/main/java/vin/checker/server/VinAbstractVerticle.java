package vin.checker.server;

import static vin.checker.constants.VinCheckerConstants.BAD_REQUEST;
import static vin.checker.constants.VinCheckerConstants.CODE;
import static vin.checker.constants.VinCheckerConstants.ERROR_MESSAGE;
import static vin.checker.constants.VinCheckerConstants.ERROR_UNABLE_TO_PROCESS_THE_REQUEST;
import static vin.checker.constants.VinCheckerConstants.MESSAGE;
import static vin.checker.constants.VinCheckerConstants.OK;

import java.sql.Connection;
import java.sql.SQLException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class VinAbstractVerticle extends AbstractVerticle {

	protected EventBus eb;
	protected Connection dbVinChecker = null;
	protected String currentAPI;

	@Override
	public void start() {
		eb = vertx.eventBus();
		log.info("Start Verticle : " + this.getClass().getName() + Thread.currentThread());
	}

	/**
	 * Establish db read Connection
	 * 
	 * @throws SQLException
	 */
	protected void getVinCheckerConnection() throws SQLException {
		dbVinChecker = VinDataSource.INSTANCE.getVinCheckerConnection();
	}

	/**
	 * api success response
	 * 
	 * @param response
	 * @return
	 */
	protected JsonObject apiSuccessResponse(JsonObject response) {
		return response
				.put(CODE, 200)
				.put(MESSAGE, response.getString(MESSAGE, OK));
	}

	/**
	 * api exception message
	 * 
	 * @param apiHandler
	 * @param ex
	 */
	protected JsonObject apiExceptionResponse() {
		return new JsonObject()
				.put(CODE, 400)
				.put(MESSAGE, BAD_REQUEST)
				.put(ERROR_MESSAGE, ERROR_UNABLE_TO_PROCESS_THE_REQUEST);        
	}
}