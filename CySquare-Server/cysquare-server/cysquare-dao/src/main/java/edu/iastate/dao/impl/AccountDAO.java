package edu.iastate.dao.impl;

import java.sql.*;

import edu.iastate.dao.Java2MySql;
import edu.iastate.domain.UserAccount;
import edu.iastate.literals.DAOLiterals;

public class AccountDAO {
	
	private Java2MySql mysqlConnector = new Java2MySql();
	
	public UserAccount getAccountInfo(String username) {
		UserAccount userAccount = new UserAccount();
		try {
			Connection conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + ".users WHERE username=\"" + username + "\";");
			if(res.next()) {
				userAccount.setUserId(res.getInt("user_id"));
				userAccount.setUsername(res.getString("username"));
				userAccount.setPassword(res.getString("password"));
				userAccount.setUpdatedTimestamp(res.getString("ts_update"));
				userAccount.setUserType(res.getString("userType"));
				userAccount.setTotalPts(res.getInt("totalPts"));
				userAccount.setApproved(res.getString("approved"));
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
			conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + ".users (`username`, `password`, `userType`) VALUES ('" + username + "', '" + password + "', '" + userType.toUpperCase() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserAccount(String username) {
		Connection conn;
		try {
			conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + ".users WHERE username='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approveUserAccount(String username) {
		Connection conn;
		try {
			conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			//Update user account with an updated string of "Y".
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + ".`users` SET `approved`='Y' WHERE  `username`='" + username + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
