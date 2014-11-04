package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.StudentCourses;
import edu.iastate.literals.DAOLiterals;

public class StudentCourseDAO extends DatabaseAccess {
		
	public List<StudentCourses> getCourses(Integer studentId) {
		
		List<StudentCourses> correlations = new ArrayList<StudentCourses>();
		StudentCourses studentCourses = new StudentCourses();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE studentid=\"" + studentId + "\";");
			if(res.next()) {
				studentCourses.setStudentId(res.getInt("studentid"));
				studentCourses.setCourseId(res.getInt("courseid"));
				correlations.add(studentCourses);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correlations;
	}
	
	public List<StudentCourses> getStudents(Integer courseId) {
		List<StudentCourses> correlations = new ArrayList<StudentCourses>();
		StudentCourses studentCourses = new StudentCourses();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE courseid=\"" + courseId + "\";");
			if(res.next()) {
				studentCourses.setStudentId(res.getInt("studentid"));
				studentCourses.setCourseId(res.getInt("courseid"));
				studentCourses.setPoints(res.getInt("points"));
				studentCourses.setUpdatedTimestamp(res.getInt("ts_update"));
				correlations.add(studentCourses);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correlations;
	}

	public void createCorrelation(Integer studentId, Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " (`studentid`, `courseid`) VALUES ('" + studentId + "', '" + courseId + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCorrelation(Integer studentId, Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE studentid='" + studentId + "' AND courseid='" + courseId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllStudentCourses(Integer studentId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE studentid='" + studentId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllCourseStudents(Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " 'WHERE courseid='" + courseId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
