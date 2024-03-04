package vin.checker.util;

import static vin.checker.constants.VinCheckerConstants.BAD_REQUEST_ERROR_CODE;
import static vin.checker.constants.VinCheckerConstants.CODE;
import static vin.checker.constants.VinCheckerConstants.MESSAGE;
import static vin.checker.constants.VinCheckerConstants.*;

import io.vertx.core.json.JsonObject;

public class VinRequestError extends RuntimeException {

	private static final long serialVersionUID = -4486885524004601317L;
    private final int errorCode;
    private final String info;

    public VinRequestError(int errorCode, String info) {
        this.errorCode = errorCode;
        this.info = info;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public String getInfo() {
		return info;
	}

    public static VinRequestError invalid(String info) {
        return new VinRequestError(BAD_REQUEST_ERROR_CODE, info);
    }
    
    public static JsonObject errorMsg(VinRequestError e) {
    	return new JsonObject()
    			.put(CODE, e.getErrorCode())
    			.put(MESSAGE, BAD_REQUEST)
    			.put(ERROR_MESSAGE, String.format(REQUEST_ERROR_MSG, e.getInfo()));
    }

}

