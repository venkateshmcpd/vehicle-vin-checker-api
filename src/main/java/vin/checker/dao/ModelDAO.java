package vin.checker.dao;

import static vin.checker.constants.VehicleAttributeConstants.MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import vin.checker.dto.AttributeData;

@Log4j2
public final class ModelDAO {

	private Connection con;
	public ModelDAO(Connection con) {
		this.con = con;
	}

	/**
	 * check if vehicle model already exists in table 
	 * if does not, insert data into model table
	 * 
	 * @param makeId
	 * @param vehicleDetails
	 * @throws SQLException
	 */
	protected void insertModel(String makeId, Map<Integer, AttributeData> vehicleDetails) throws SQLException {
 		AttributeData attributeData = vehicleDetails.get(MODEL);
 		// check if make already exists
		String selectQuery =
				"SELECT id \n" 
				+ " FROM model \n"
				+ "	WHERE id = ?";
		boolean recordExists = false;
		try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
			ps.setString(1, attributeData.getValueId());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					recordExists = true;
					log.info("Vehicle Model found in table");
				}
			}
		}
 		
		if(!recordExists) {
			log.info("Insert new Vehicle Model");
			String insertQuery = 
					"INSERT INTO model ( \n"
					+ "    id, \n"
					+ "    name, \n"
					+ "    make_id \n"
					+ ") \n"
					+ "VALUES (?, ?, ?)";
			try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
				ps.setString(1, attributeData.getValueId());
				ps.setString(2, attributeData.getValue());
				ps.setString(3, makeId);
				ps.executeUpdate();
			}
		}
	}
}