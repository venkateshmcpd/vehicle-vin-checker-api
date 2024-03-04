package vin.checker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import lombok.extern.log4j.Log4j2;
import vin.checker.dto.RequestErrorLog;

@Log4j2
public final class RequestErrorLogDAO {

	private Connection con;
	public RequestErrorLogDAO(Connection con) {
		this.con = con;
	}
	
	/**
	 * insert request error log details into api_request_error_log table
	 * 
	 * @param requestErrorLog
	 * @throws SQLException
	 */
	public void insertRequestErrorLog(RequestErrorLog requestErrorLog) throws SQLException {		
		log.info("Insert new request error log");
		String sql = 
				"INSERT INTO api_request_error_log ( \n"
				+ "    error_type_id, \n"
				+ "    vehicle_info_id, \n"
				+ "    requested_vin, \n"
				+ "    requested_make, \n"
				+ "    requested_model, \n"
				+ "    requested_model_year, \n"
				+ "    error_code, \n"
				+ "    error_text \n"
				+ ") \n"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		int paramCounter = 0;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(++paramCounter, requestErrorLog.getErrorTypeId());
			if(requestErrorLog.getVehicleInfoId() != null) {
				ps.setInt(++paramCounter, requestErrorLog.getVehicleInfoId());
			} else {
				ps.setNull(++paramCounter, Types.INTEGER);
			}
			ps.setString(++paramCounter, requestErrorLog.getRequestedVin());
			ps.setString(++paramCounter, requestErrorLog.getRequestedMake());
			ps.setString(++paramCounter, requestErrorLog.getRequestedModel());
			ps.setString(++paramCounter, requestErrorLog.getRequestedModelYear());
			ps.setString(++paramCounter, requestErrorLog.getErrorCode());
			ps.setString(++paramCounter, requestErrorLog.getErrorText());
			ps.executeUpdate();
		}
	}
}