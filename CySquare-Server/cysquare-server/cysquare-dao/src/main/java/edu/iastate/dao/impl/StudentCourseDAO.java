package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.StudentCourse;
import edu.iastate.literals.DAOLiterals;

public class StudentCourseDAO extends DatabaseAccess {
		
	public List<StudentCourse> getCourses(Integer studentId) {
		
		List<StudentCourse> correlations = new ArrayList<StudentCourse>();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE studentid=\"" + studentId + "\";");
			if(res.next()) {
				StudentCourse studentCourse = populateStudentCourses(res);
				correlations.add(studentCourse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correlations;
	}

	public List<StudentCourse> getStudents(Integer courseId) {
		List<StudentCourse> correlations = new ArrayList<StudentCourse>();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE courseid=\"" + courseId + "\";");
			if(res.next()) {
				StudentCourse studentCourse = populateStudentCourses(res);
				correlations.add(studentCourse);
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
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_STUDCOURSES + " WHERE studentid='" + studentId + "' AND courseid='" + courseId + "';");
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
