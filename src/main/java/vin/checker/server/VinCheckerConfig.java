package vin.checker.server;

import static vin.checker.constants.VinCheckerConstants.*;
import static vin.checker.constants.VinCheckerConstants.SQL_CONFIG;
import static vin.checker.constants.VinCheckerConstants.VIN_CHECKER_API_HOST;
import static vin.checker.constants.VinCheckerConstants.VIN_CHECKER_API_PORT;

import io.vertx.core.json.JsonObject;

public final class VinCheckerConfig {

	public static String getHttpProtocol() {
		return httpProtocol;
	}

	public static void setHttpProtocol(String httpProtocol) {
		VinCheckerConfig.httpProtocol = httpProtocol;
	}

	private static String vinCheckerApiHost;
	private static Integer vinCheckerApiPort;
	private static JsonObject sqlConfig;
	private static String vpicApiUrl;
	private static String httpProtocol;

    public static void setConfigValues(JsonObject config) {
		JsonObject appConfig = config.getJsonObject(APP_CONFIG);
    	vinCheckerApiHost = appConfig.getString(VIN_CHECKER_API_HOST);
    	vinCheckerApiPort = appConfig.getInteger(VIN_CHECKER_API_PORT);
    	sqlConfig = config.getJsonObject(SQL_CONFIG);
    	vpicApiUrl = appConfig.getString(VPIC_API_URL);
    	httpProtocol = appConfig.getString("http_protocal");
    }

	public static String getVinCheckerApiHost() {
		return vinCheckerApiHost;
	}

	public static void setVinCheckerApiHost(String vinCheckerApiHost) {
		VinCheckerConfig.vinCheckerApiHost = vinCheckerApiHost;
	}

	public static Integer getVinCheckerApiPort() {
		return vinCheckerApiPort;
	}

	public static void setVinCheckerApiPort(Integer vinCheckerApiPort) {
		VinCheckerConfig.vinCheckerApiPort = vinCheckerApiPort;
	}

	public static JsonObject getSqlConfig() {
		return sqlConfig;
	}
	public static void setSqlConfig(JsonObject sqlConfig) {
		VinCheckerConfig.sqlConfig = sqlConfig;
	}

	public static String getVpicApiUrl() {
		return vpicApiUrl;
	}

	public static void setVpicApiUrl(String vpicApiUrl) {
		VinCheckerConfig.vpicApiUrl = vpicApiUrl;
	}
	
	private VinCheckerConfig() {
		
	}
}
