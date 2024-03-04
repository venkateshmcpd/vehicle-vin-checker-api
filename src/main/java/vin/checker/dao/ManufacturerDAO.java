package vin.checker.dao;

import static vin.checker.constants.VehicleAttributeConstants.MANUFACTURER_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import vin.checker.dto.AttributeData;

@Log4j2
public final class ManufacturerDAO {

	private Connection con;
	public ManufacturerDAO(Connection con) {
		this.con = con;
	}

	/**
	 * check if manufacturer already exists in table 
	 * if does not, insert data into manufacturer table
	 * 
	 * @param vehicleDetails
	 * @return
	 * @throws SQLException
	 */
	protected String insertManufacturer(Map<Integer, AttributeData> vehicleDetails) throws SQLException {
 		AttributeData attributeData = vehicleDetails.get(MANUFACTURER_NAME);
 		// check if manufacturer already exists
		String selectQuery =
				"SELECT id \n" 
				+ " FROM manufacturer \n"
				+ "	WHERE id = ?";
		boolean recordExists = false;
		try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
			ps.setString(1, attributeData.getValueId());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					recordExists = true;
					log.info("Vehicle Manufacturer found in table");
				}
			}
		}
 		
		if(!recordExists) {
			log.info("Insert new Vehicle Manufacturer");
			String insertQuery = 
					"INSERT INTO manufacturer ( \n"
					+ "    id, \n"
					+ "    name \n"
					+ ") \n"
					+ "VALUES (?, ?)";
			try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
				ps.setString(1, attributeData.getValueId());
				ps.setString(2, attributeData.getValue());
				ps.executeUpdate();
			}
		}
		return attributeData.getValueId();
	}
}