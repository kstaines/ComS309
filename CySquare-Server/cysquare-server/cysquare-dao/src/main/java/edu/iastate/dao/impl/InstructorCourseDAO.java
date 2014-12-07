package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.InstructorCourse;
import edu.iastate.literals.DAOLiterals;

public class InstructorCourseDAO extends DatabaseAccess {

	public List<InstructorCourse> getCourses(Integer instructorId) {
		
		List<InstructorCourse> correlations = new ArrayList<InstructorCourse>();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " WHERE instructorid=" + instructorId + ";");
			while(res.next()) {
				InstructorCourse instructorCourse = populateInstructorCourses(res);
				correlations.add(instructorCourse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correlations;
	}
	
	public List<InstructorCourse> getInstructors(Integer courseId) {
		
		List<InstructorCourse> correlations = new ArrayList<InstructorCourse>();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " WHERE courseid=" + courseId + ";");
			while(res.next()) {
				InstructorCourse instructorCourse = populateInstructorCourses(res);
				correlations.add(instructorCourse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correlations;
	}
	
	public void createCorrelation(Integer instructorId, Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " (`instructorid`, `courseid`) VALUES (" + instructorId + ", " + courseId + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCorrelation(Integer instructorId, Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " WHERE instructorid=" + instructorId + " AND courseid=" + courseId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllInstructorCourses(Integer instructorId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " WHERE instructorid=" + instructorId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllCourseInstructors(Integer courseId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_INSTCOURSES + " WHERE courseid=" + courseId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
