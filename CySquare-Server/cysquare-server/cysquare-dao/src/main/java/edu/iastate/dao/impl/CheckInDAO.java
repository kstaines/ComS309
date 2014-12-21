package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.CheckIn;
import edu.iastate.literals.DAOLiterals;

public class CheckInDAO extends DatabaseAccess {

	/**
	 * Gets a list of all current check-ins
	 * @return
	 */
	public List<CheckIn> getAllCurrentCheckIns() {
		List<CheckIn> checkIns = new ArrayList<CheckIn>();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_CHECKIN + ";");
			while(res.next()) {
				CheckIn checkIn = populateCheckIn(res);
				checkIns.add(checkIn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return checkIns;
	}
	
	/**
	 * Gets a check-in object that contains the information
	 * of a particular student's last check-in.
	 * @param userId
	 * @return
	 */
	public CheckIn getCurrentUserCheckIn(int userId) {
		CheckIn checkIn = new CheckIn();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_CHECKIN + " WHERE studentid=" + userId + ";");
			if(res.next()) {
				checkIn = populateCheckIn(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return checkIn;
	}
	
	/**
	 * Updates the user's most recent check-in information.
	 * This will only update the database, use createUserCheckIn to create the check-in
	 * entry for the first time of that user.
	 * @param userId
	 * @param courseId
	 * @param latitude
	 * @param longitude
	 */
	public void updateUserCheckIn(int userId, int courseId, float latitude, float longitude) {
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeQuery("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_CHECKIN + " SET `courseid`=" + courseId + ", `latitude`=" + latitude + ", `longitude`=" + longitude + " WHERE `studentid`=" + userId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates a new entry for a user check-in. Use this method to create the check-in
	 * information for the first time, all other times use the updateUserCheckIn to just
	 * update the information.
	 * @param userId
	 * @param courseId
	 * @param latitude
	 * @param longitude
	 */
	public void createUserCheckIn(int userId, int courseId, float latitude, float longitude) {
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeQuery("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_CHECKIN + "(`studentid`, `courseid`, `latitude`, `longitude`) VALUES (" + userId + ", " + courseId + ", " + latitude + ", " + longitude + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
