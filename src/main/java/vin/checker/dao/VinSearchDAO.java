package vin.checker.dao;

import static vin.checker.constants.VehicleAttributeConstants.ERROR_CODE;
import static vin.checker.constants.VehicleAttributeConstants.ERROR_TEXT;
import static vin.checker.constants.VehicleAttributeConstants.MAKE;
import static vin.checker.constants.VehicleAttributeConstants.MODEL;
import static vin.checker.constants.VehicleAttributeConstants.MODEL_YEAR;
import static vin.checker.constants.VehicleAttributeConstants.VEHICLE_INFO_ID;
import static vin.checker.constants.VehicleAttributeConstants.VIN_VARIABLE_IDS;
import static vin.checker.constants.VinCheckerConstants.PATH_SEGMENTS;
import static vin.checker.constants.VinCheckerConstants.*;
import static vin.checker.constants.VinCheckerConstants.REQUESTED_DATA_MISMATCH;
import static vin.checker.constants.VinCheckerConstants.VIN_NOT_FOUND;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;
import vin.checker.dto.AttributeData;
import vin.checker.dto.RequestErrorLog;
import vin.checker.server.VinCheckerConfig;
import vin.checker.service.HttpClientService;
import vin.checker.util.JsonUtils;

@Log4j2
public final class VinSearchDAO {

	private Connection con;
	private MakeDAO makeDAO;
	private ModelDAO modelDAO;
	private ManufacturerDAO manufacturerDAO;
	private VehicleInfoDAO vehicleInfoDAO;
	private RequestErrorLogDAO requestErrorLogDAO;
	public VinSearchDAO(Connection con) {
		this.con = con;
		this.makeDAO = new MakeDAO(con);
		this.modelDAO = new ModelDAO(con);
		this.manufacturerDAO = new ManufacturerDAO(con);
		this.vehicleInfoDAO = new VehicleInfoDAO(con);
		this.requestErrorLogDAO = new RequestErrorLogDAO(con);		
	}
	
	/**
	 * Verify if requested VIN exists in local database
	 * In the event VIN does not exist in local database, call the VPIC API to get the vehicle details based on VIN
	 * If VIN found, then check whether other requested details like Make, Model & Model Year match with the details from db/api
	 * If does not match, log it in request error log table
	 * If VIN not found, log it in request error log table
	 * 
	 * @param messageBody
	 * @return
	 * @throws Exception
	 */
	public JsonObject searchVin(JsonObject messageBody) throws Exception {
		String vin = JsonUtils.getBodyStringValue(messageBody, "vin");
		String make = JsonUtils.getBodyStringValue(messageBody, "make");
		String model = JsonUtils.getBodyStringValue(messageBody, "model");
		String modelYear = JsonUtils.getBodyStringValue(messageBody, "model_year");
		String status = null;

		log.info("Get Vehicle details from local db based on VIN");		
		Map<Integer, AttributeData> vehicleDetails = getVehicleDetailsFromDb(vin);

		// VIN does not exist in local db,
		boolean vinFound = true;
		if(vehicleDetails.isEmpty()) {
			log.info("VIN does not exist in local db");		
			log.info("Get Vehicle/VIN details from VPIC API");		
			vehicleDetails = getVehicleDetailsFromVpicApi(vin);
			
			// check if api returns vin details with error code = 0
			log.info("Error Code from VPIC API response: " + vehicleDetails.get(ERROR_CODE).getValue());
			if(vehicleDetails.get(ERROR_CODE).getValue().equals("0")) {
				// insert into local db
				log.info("VIN found in VPIC data, Inserted into local db");
				insertVehicleDetails(vin, vehicleDetails);
				status = "Inserted a new record from VPIC catalog to local database";
			} else {
				vinFound = false;
			}
		} else {
			status = "VIN found in local database";
		}
		
		// validate vehicle/vin details with requested data
		// if there is any mismatch in data then log it in request error log table
		if(!make.equals(vehicleDetails.get(MAKE).getValue())
				|| !model.equals(vehicleDetails.get(MODEL).getValue())
				|| !modelYear.equals(vehicleDetails.get(MODEL_YEAR).getValue())
				|| !vinFound) {
			log.info("Found requested data mismatch, log it in request error log table");		
			// log request details to error log table
			Integer errorTypeId = !vinFound ? VIN_NOT_FOUND : REQUESTED_DATA_MISMATCH;
			status = !vinFound ? "VIN not found in VPIC catalog" : "Requested data mismatched";
			Integer vehicleInfoId = vehicleDetails.get(VEHICLE_INFO_ID) != null ? Integer.parseInt(vehicleDetails.get(VEHICLE_INFO_ID).getValue()) : null;
			RequestErrorLog requestErrorLog = RequestErrorLog.builder()
					.errorTypeId(errorTypeId)
					.vehicleInfoId(vehicleInfoId)
					.requestedVin(vin)
					.requestedMake(make)
					.requestedModel(model)
					.requestedModelYear(modelYear)					
					.build();
			if(vehicleDetails.containsKey(ERROR_CODE)) {
				requestErrorLog.setErrorCode(vehicleDetails.get(ERROR_CODE).getValue());
				requestErrorLog.setErrorText(vehicleDetails.get(ERROR_TEXT).getValue());
			}
			requestErrorLogDAO.insertRequestErrorLog(requestErrorLog);
		}
		return new JsonObject()
				.put(MESSAGE, status);
	}
	
	/**
	 * Get vehicle details from local db based on VIN 
	 * 
	 * @param vin
	 * @return
	 * @throws SQLException
	 */
	private Map<Integer, AttributeData> getVehicleDetailsFromDb(String vin) throws SQLException {
		Map<Integer, AttributeData> vehicleDetails = new HashMap<>();
		String sql =
				"SELECT \n" 
				+ "		vi.id AS vehicle_info_id, \n"
				+ "		vi.vin AS vin, \n"
				+ "		vi.model_year AS model_year, \n"
				+ "		mk.id AS make_id, \n"
				+ "		mk.name AS make, \n"
				+ "		md.id AS model_id, \n"
				+ "		md.name AS model \n"
				+ " FROM vehicle_info vi \n"
				+ " 	INNER JOIN manufacturer mf ON mf.id = vi.manufacturer_id \n"
				+ " 	INNER JOIN make mk ON mk.manufacturer_id = mf.id \n"
				+ " 	INNER JOIN model md ON md.make_id = mk.id \n"
				+ "	WHERE vi.vin = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, vin);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					vehicleDetails.put(VEHICLE_INFO_ID, new AttributeData("", rs.getString("vehicle_info_id")));
					vehicleDetails.put(MAKE, new AttributeData(rs.getString(MAKE_ID), rs.getString("make")));
					vehicleDetails.put(MODEL, new AttributeData(rs.getString(MODEL_ID), rs.getString("model")));
					vehicleDetails.put(MODEL_YEAR, new AttributeData("", rs.getString("model_year")));
				}
			}
		}
		return vehicleDetails;
	}
	
	/**
	 * Get vehicle details from VPIC API based on VIN 
	 * 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, AttributeData> getVehicleDetailsFromVpicApi(String vin) throws Exception {
		JsonObject requestBody = new JsonObject()
				.put(PATH_SEGMENTS, new JsonArray()
						.add(API)
						.add(VEHICLES)
						.add(DECODE_VIN)
						.add(vin))
				.put(QUERY_PARAMS, new JsonObject()
						.put(FORMAT, JSON));
		JsonObject response = HttpClientService.getGetApiResponse(VinCheckerConfig.getVpicApiUrl(), requestBody);
		return response.getJsonArray(RESULTS).stream()
				.map(JsonObject.class::cast)
				.filter(obj -> VIN_VARIABLE_IDS.contains(obj.getInteger(VARIABLE_ID)))
				.collect(Collectors.toMap(entry -> entry.getInteger(VARIABLE_ID), entry -> new AttributeData(entry.getString(VALUE_ID), entry.getString(VALUE))));
	}
	
	/**
	 * insert data into manufacturer, vehicle info, make & model tables
	 * 
	 * @param vin
	 * @param vehicleDetails
	 * @throws SQLException
	 */
	private void insertVehicleDetails(String vin, Map<Integer, AttributeData> vehicleDetails) throws SQLException {
		// insert into manufacturer
		String manufacturerId = manufacturerDAO.insertManufacturer(vehicleDetails);
		
		// insert into make
		String makeId = makeDAO.insertMake(manufacturerId, vehicleDetails);

		// insert into model
		modelDAO.insertModel(makeId, vehicleDetails);

		// insert into vehicle info
		Integer vehicleInfoId = vehicleInfoDAO.insertVehicleInfo(vin, manufacturerId, vehicleDetails);
		vehicleDetails.put(VEHICLE_INFO_ID, new AttributeData("", String.valueOf(vehicleInfoId)));
	}
}