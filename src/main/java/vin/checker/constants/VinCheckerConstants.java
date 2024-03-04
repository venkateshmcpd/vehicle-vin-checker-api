package vin.checker.constants;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import io.vertx.core.eventbus.ReplyFailure;

public class VinCheckerConstants {

	public static final String LOCALE = "locale";
	public static final String LOCALE_EN = "en";
	public static final String CONTENT_TYPE = "content-type";
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	public static final String BODY = "body";
	public static final String PATH_SEGMENTS = "path_segments";
	public static final String QUERY_PARAMS = "query_params";
	public static final String CODE = "code";
	public static final String OK = "OK";
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final String EMPTY_STRING = "";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String USER = "user";
	public static final String PROTOCAL = "protocal";
    public static final Integer BAD_REQUEST_ERROR_CODE = 400;
    public static final String REQUEST_ERROR_MSG = "'%s' is missing or invalid";

	public static final String DRIVER_CLASS_NAME = "driver_class_name";
	public static final String JDBC_URL = "jdbc_url";
	public static final String USER_NAME = "user_name";
	public static final String PASSWORD = "password";
	public static final String MAX_CON_POOL_SIZE = "max_con_pool_size";
	public static final String CONNECTION_TIME_OUT = "connection_time_out";
	public static final String CACHE_PREP_STMTS_KEY = "cachePrepStmts";
	public static final String PREP_STMT_CACHE_SIZE_KEY = "prepStmtCacheSize";
	public static final String PREP_STMT_CACHE_SQL_LIMIT_KEY = "prepStmtCacheSqlLimit";
	public static final String CACHE_PREP_STMTS = "cache_prep_stmts";
	public static final String PREP_STMT_CACHE_SIZE = "prep_stmt_cache_size";
	public static final String PREP_STMT_CACHE_SQL_LIMIT = "prep_stmt_cache_sql_limit";
	
	public static final String NOT_FOUND = "NOT FOUND";
	public static final String API_NOT_FOUND = "API NOT FOUND";
	public static final String TIME_OUT = "TIME OUT";
	public static final String RECIPIENT_FAILURE = "RECIPIENT FAILURE";	
	public static final String ERROR = "ERROR";
	public static final String BAD_REQUEST = "BAD REQUEST";
	public static final String ERROR_MESSAGE = "error_message";
    public static final String VALIDATION_ERROR = "validation_error";
	public static final String EXCEPTION = "Exception ";
	public static final String MESSAGE = "message";
	public static final String ERROR_UNABLE_TO_PROCESS_THE_REQUEST = "Unable to process the request at this time, please try again later";
	public static final String UNABLE_TO_PROCESS_THE_REQUEST = "UNABLE TO PROCESS THE REQUEST";
	public static final String SUCCESS_RESPONSE = "SUCCESS RESPONSE: API: %s, Processing Time: %s";
	public static final String FAILURE_RESPONSE = "FAILURE RESPONSE: API: %s, Processing Time: %s";
	public static final String REPLY_FAILURE = "REPLY FAILURE: API: %s, Processing Time: %s";
	public static final String UNKNOWN_ERROR_CODE = "Unknown error code, message not defined";
	public static final String NOTIFY_SUPPORT = "NOTIFY SUPPORT : Exception in %s";
	public static final String UNAUTHORIZED = "UNAUTHORIZED";
		
	public static final String SERVER_CONFIG = "server_config";
	public static final String APP_CONFIG = "app_config";
	public static final String SQL_CONFIG = "sql_config";
	public static final String VIN_CHECKER_API_HOST = "vin_checker_api_host";
	public static final String VIN_CHECKER_API_PORT = "vin_checker_api_port";
	public static final String VPIC_API_URL = "vpic_api_url";
	
	public static final String WORKER_POOL_SIZE = "worker_pool_size";
	public static final String CLASS = "class";
	public static final String WORKERS = "workers";
	public static final String INSTANCES = "instances";
	public static final String MESSAGES_BUNDLE = "i18n.MessagesBundle";
	public static final String DEPLOYING_WORKER = "Deploying Worker: %s";
	public static final Integer VIN_NOT_FOUND = 1;
	public static final Integer REQUESTED_DATA_MISMATCH = 2;

	public static final String VARIABLE_ID = "VariableId";
	public static final String VEHICLES = "vehicles";
	public static final String DECODE_VIN = "DecodeVin";
	public static final String API = "api";
	public static final String JSON = "json";
	public static final String FORMAT = "format";
	public static final String RESULTS = "Results";
	public static final String VALUE_ID = "ValueId";
	public static final String VALUE = "Value";
//	public static final String VEHICLE_INFO_ID = "vehicle_info_id";
//	public static final String MAKE = "make";
	public static final String MAKE_ID = "make_id";
//	public static final String MODEL = "model";
	public static final String MODEL_ID = "model_id";
//	public static final String MODEL_YEAR = "model_year";

    public static final Map<Integer, String> RESPONSE_MESSAGES_MAP = ImmutableMap.<Integer, String>builder()
            .put(200, SUCCESS_RESPONSE)
    		.put(400, FAILURE_RESPONSE)
    		.put(404, REPLY_FAILURE)
            .build();
	public static final Map<ReplyFailure, String> REPLY_FAILURES_MAP = ImmutableMap.<ReplyFailure, String>builder()
			.put(ReplyFailure.NO_HANDLERS, NOT_FOUND)
			.put(ReplyFailure.TIMEOUT, TIME_OUT)
			.put(ReplyFailure.ERROR, ERROR)
			.put(ReplyFailure.RECIPIENT_FAILURE, RECIPIENT_FAILURE)
			.build();
	/**
	 * empty private constructor
	 */
	private VinCheckerConstants() {
		
	}
}
