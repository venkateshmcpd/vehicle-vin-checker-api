package vin.checker.dao;

import static vin.checker.constants.VehicleAttributeConstants.MODEL_YEAR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import vin.checker.dto.AttributeData;

@Log4j2
public final class VehicleInfoDAO {

	private Connection con;
	public VehicleInfoDAO(Connection con) {
		this.con = con;
	}

	/**
	 * insert data into vehicle info table
	 * 
	 * @param vin
	 * @param manufacturerId
	 * @param vehicleDetails
	 * @return
	 * @throws SQLException
	 */
	protected Integer insertVehicleInfo(String vin, String manufacturerId, Map<Integer, AttributeData> vehicleDetails) throws SQLException {
		log.info("Insert new Vehicle Info");
		String sql = 
				"INSERT INTO vehicle_info ( \n"
				+ "    vin, \n"
				+ "    manufacturer_id, \n"
				+ "    model_year \n"
				+ ") \n"
				+ "OUTPUT INSERTED.id \n"
				+ "VALUES (?, ?, ?)";
		Integer vehicleInfoId = null;
		Integer paramCounter = 0;		
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(++paramCounter, vin);
			ps.setString(++paramCounter, manufacturerId);
			ps.setString(++paramCounter, vehicleDetails.get(MODEL_YEAR).getValue());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					vehicleInfoId = rs.getInt(1);
				}
			}
		}
		return vehicleInfoId;
	}
}