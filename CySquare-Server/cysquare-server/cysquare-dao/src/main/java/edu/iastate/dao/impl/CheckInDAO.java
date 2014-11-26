package edu.iastate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.CheckIn;

public class CheckInDAO extends DatabaseAccess {

	/**
	 * Gets a list of all current check-ins
	 * @return
	 */
	public List<CheckIn> getAllCurrentCheckIns() {
		return new ArrayList<CheckIn>();
	}
	
	/**
	 * Gets a check-in object that contains the information
	 * of a particular student's last check-in.
	 * @param userId
	 * @return
	 */
	public CheckIn getCurrentUserCheckIn(int userId) {
		return new CheckIn();
	}
}
