package vin.checker.dao;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;
import vin.checker.config.builder.ConfigReader;
import vin.checker.server.VinCheckerConfig;
import vin.checker.server.VinDataSource;

@Log4j2
class VinSearchDAOTest {

	private static Connection con;
	private VinSearchDAO searchDAO;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JsonObject config = ConfigReader.INSTANCE.getSqlConfig(); 
		VinCheckerConfig.setConfigValues(config);
		VinDataSource.INSTANCE.buildDataSource();
		con = VinDataSource.INSTANCE.getVinCheckerConnection();
	}
	
	@Test
	void testGetVinData() throws Exception {
		searchDAO = new VinSearchDAO(con);
		JsonObject messageBody = new JsonObject()
				.put("vin", "WDBEA36E0NB682816")
				.put("make", "MERCEDES-BENZ1")
				.put("model", "500")
				.put("model_year", "1992");
		JsonObject response = searchDAO.searchVin(messageBody);
		log.info(response);
	}

	@Test
	void testGetVinData2() throws Exception {
		searchDAO = new VinSearchDAO(con);
		JsonObject messageBody = new JsonObject()
				.put("vin", "WUAS6!F!1EA900006")
				.put("make", "MERCEDES-BENZ1")
				.put("model", "500")
				.put("model_year", "1992");
		JsonObject response = searchDAO.searchVin(messageBody);
		log.info(response);
	}

	@Test
	void testGetVinData3() throws Exception {
		searchDAO = new VinSearchDAO(con);
		JsonObject messageBody = new JsonObject()
				.put("vin", "ZFFKW64A080466277")
				.put("make", "MERCEDES-BENZ1")
				.put("model", "500")
				.put("model_year", "1992");
		System.out.println(messageBody);
		JsonObject response = searchDAO.searchVin(messageBody);
		log.info(response);
	}
}
