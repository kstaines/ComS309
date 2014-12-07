package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.UserAccount;
import edu.iastate.literals.DAOLiterals;

public class AccountDAO extends DatabaseAccess {
	
	public UserAccount getAccountInfo(String username) {
		UserAccount userAccount = new UserAccount();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE username=\"" + username + "\";");
			if(res.next()) {
				userAccount = populateUserAccount(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userAccount;
	}

	public UserAccount getAccountInfoById(Integer userId) {
		UserAccount userAccount = new UserAccount();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE userid=\"" + userId + "\";");
			if(res.next()) {
				userAccount = populateUserAccount(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userAccount;
	}
	
	public void createUserAccount(String username, String password, String userType) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " (`username`, `password`, `userType`) VALUES ('" + username + "', '" + password + "', '" + userType.toUpperCase() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserAccount(String username) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE username='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserAccountId(Integer userId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE userid='" + userId + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approveUserAccount(String username) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			//Update user account with an updated string of "Y".
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " SET `approved`='Y' WHERE  `username`='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disapproveUserAccount(String username) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			//Update user account with an updated string of "N".
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " SET `approved`='N' WHERE  `username`='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserAccount> getUnapprovedUsers() {
		List<UserAccount> users = new ArrayList<UserAccount>();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE approved='N' OR approved='n';");
			while(res.next()) {
				UserAccount userAccount = populateUserAccount(res);
				users.add(userAccount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public List<UserAccount> getApprovedUsers() {
		List<UserAccount> users = new ArrayList<UserAccount>();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " WHERE approved='Y' OR approved='y';");
			while(res.next()) {
				UserAccount userAccount = populateUserAccount(res);
				users.add(userAccount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public List<UserAccount> getAllUsers() {
		List<UserAccount> users = new ArrayList<UserAccount>();
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + ";");
			while(res.next()) {
				UserAccount userAccount = populateUserAccount(res);
				users.add(userAccount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	/**
	 * Updates the value of the current points in the database by adding the parameter's value (newPoints)
	 * to the existing point value. Also takes in the username. If needing to update by user ID, use updatePointValueById().
	 * @param username
	 * @param newPoints
	 */
	public void updatePointValue(String username, Integer newPoints) {
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " SET `totalPts`=`totalPts` + " + newPoints + " WHERE `username`='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePointValueById(Integer userId, Integer newPoints) {
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " SET `totalPts`=`totalPts` + " + newPoints + " WHERE `userId`='" + userId + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetStudentPoints() {
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_USERS + " SET `totalPts`=0 WHERE `userType`='STUDENT';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
