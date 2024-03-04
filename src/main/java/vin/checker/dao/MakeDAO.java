package vin.checker.dao;

import static vin.checker.constants.VehicleAttributeConstants.MAKE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import vin.checker.dto.AttributeData;

@Log4j2
public final class MakeDAO {

	private Connection con;
	public MakeDAO(Connection con) {
		this.con = con;
	}

	/**
	 * check if vehicle make already exists in table 
	 * if does not, insert data into make table
	 * 
	 * @param manufacturerId
	 * @param vehicleDetails
	 * @return
	 * @throws SQLException
	 */
	protected String insertMake(String manufacturerId, Map<Integer, AttributeData> vehicleDetails) throws SQLException {
 		AttributeData attributeData = vehicleDetails.get(MAKE);
 		// check if make already exists
		String selectQuery =
				"SELECT id \n" 
				+ " FROM make \n"
				+ "	WHERE id = ?";
		boolean recordExists = false;
		try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
			ps.setString(1, attributeData.getValueId());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					recordExists = true;
					log.info("Vehicle Make found in table");
				}
			}
		}
 		
		// if vehicle make does not exists in database, then insert a new record 
		if(!recordExists) {
			log.info("Insert new Vehicle Make");
			String insertQuery = 
					"INSERT INTO make ( \n"
					+ "    id, \n"
					+ "    name, \n"
					+ "    manufacturer_id \n"
					+ ") \n"
					+ "VALUES (?, ?, ?)";
			try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
				ps.setString(1, attributeData.getValueId());
				ps.setString(2, attributeData.getValue());
				ps.setString(3, manufacturerId);
				ps.executeUpdate();
			}
		}
		return attributeData.getValueId();
	}
}