package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.iastate.dao.Java2MySql;
import edu.iastate.domain.Course;
import edu.iastate.literals.DAOLiterals;

public class CourseDAO {

private Java2MySql mysqlConnector = new Java2MySql();
	
	public Course getCourseInfo(String courseName) {
		Course course = new Course();
		try {
			Connection conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_COURSES + " WHERE name=\"" + courseName + "\";");
			if(res.next()) {
				course.setCourseId(res.getInt("courseid"));
				course.setName(res.getString("name"));
				course.setLocation(res.getString("location"));
				course.setUpdatedTimestamp(res.getString("ts_update"));
				course.setTime(res.getString("time"));
				course.setDays(res.getString("days"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return course;
	}

	public void createCourse(String name, String location, String time, String days) {
		Connection conn;
		try {
			conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_COURSES + " (`name`, `location`, `time`, `days`) VALUES ('" + name + "', '" + location + "', '" + time + "', '" + days + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCourse(String name) {
		Connection conn;
		try {
			conn = mysqlConnector.makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_COURSES + " WHERE name='" + name + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
