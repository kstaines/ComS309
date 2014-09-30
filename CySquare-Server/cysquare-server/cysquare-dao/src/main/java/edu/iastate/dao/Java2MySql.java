package edu.iastate.dao;

import java.sql.*;

import edu.iastate.literals.*;

public class Java2MySql {
	
	private Connection connection;
	
	public Connection makeConnection() throws Exception {
		String url = DAOLiterals.MYSQL_URL;
		String dbName = DAOLiterals.MYSQL_DB_NAME;
		String driver = DAOLiterals.MYSQL_DRIVER;
		String username = DAOLiterals.MYSQL_USERNAME;
		String password = DAOLiterals.MYSQL_PASSWORD;
		
		try{
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url+dbName, username, password);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return connection;
	}
	
	public void closeConnection() throws Exception {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new Exception("Database connection could not be closed." + e);
		}
	}
}
