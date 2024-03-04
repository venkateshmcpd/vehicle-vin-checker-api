package vin.checker.verticle;

import static vin.checker.constants.VinCheckerApiConstants.API_POST_SEARCH_VIN;
import static vin.checker.constants.VinCheckerConstants.EXCEPTION;
import static vin.checker.constants.VinCheckerConstants.NOTIFY_SUPPORT;

import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;
import vin.checker.dao.VinSearchDAO;
import vin.checker.server.VinAbstractVerticle;
import vin.checker.util.VinRequestError;

@Log4j2
public class VinSearch extends VinAbstractVerticle {

    @Override
    public void start() {
        super.start();

        /**
         * api to check vin exists, if not, read from vpic and insert into local tables
         */
        eb.consumer(API_POST_SEARCH_VIN).handler(message -> {
            currentAPI = API_POST_SEARCH_VIN;
            try {
                JsonObject messageBody = new JsonObject((String) message.body());
                JsonObject response = searchVin(messageBody);
                message.reply(response);
                return;
			} catch (VinRequestError e) {
				JsonObject response = VinRequestError.errorMsg(e);
				message.reply(response);
				return;
            } catch (Exception e) {            	
                log.error(String.format(NOTIFY_SUPPORT, currentAPI));
                log.error(EXCEPTION, e);            
                message.reply(apiExceptionResponse());
                return;
            }
        });
    }

    /**
     * Search VIN, if not found, then read from VPIC and insert into local tables
     *
     * @param messageBody
     * @return
     * @throws Exception
     */
    private JsonObject searchVin(JsonObject messageBody) throws Exception {
        getVinCheckerConnection();
        VinSearchDAO searchDAO = new VinSearchDAO(dbVinChecker);
        JsonObject response = searchDAO.searchVin(messageBody);
        return apiSuccessResponse(response);
    }  
}
