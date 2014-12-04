package edu.iastate.dao.ifc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.iastate.domain.CheckIn;
import edu.iastate.domain.Course;
import edu.iastate.domain.Friend;
import edu.iastate.domain.InstructorCourse;
import edu.iastate.domain.StudentCourse;
import edu.iastate.domain.UserAccount;
import edu.iastate.literals.DAOLiterals;

public abstract class DatabaseAccess {

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

	protected UserAccount populateUserAccount(ResultSet res) throws SQLException {
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(res.getInt("userid"));
		userAccount.setUsername(res.getString("username"));
		userAccount.setPassword(res.getString("password"));
		userAccount.setUpdatedTimestamp(res.getString("ts_update"));
		userAccount.setUserType(res.getString("userType"));
		userAccount.setTotalPts(res.getInt("totalPts"));
		userAccount.setApproved(res.getString("approved"));
		return userAccount;
	}

	protected Course populateCourse(ResultSet res) throws SQLException {
		Course course = new Course();
		course.setCourseId(res.getInt("courseid"));
		course.setName(res.getString("name"));
		course.setSection(res.getString("section"));
		course.setLocation(res.getString("location"));
		course.setUpdatedTimestamp(res.getString("ts_update"));
		course.setTime(res.getString("time"));
		course.setDays(res.getString("days"));
		return course;
	}

	protected Friend populateFriend(ResultSet res) throws SQLException {
		Friend friend = new Friend();
		friend.setStudentId(res.getInt("studentid"));
		friend.setFriendId(res.getInt("friendid"));
		friend.setApproved(res.getString("approved"));
		friend.setUpdatedTimestamp(res.getString("ts_update"));
		return friend;
	}

	protected StudentCourse populateStudentCourses(ResultSet res) throws SQLException {
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setStudentId(res.getInt("studentid"));
		studentCourse.setCourseId(res.getInt("courseid"));
		studentCourse.setPoints(res.getInt("points"));
		studentCourse.setTimesCheckedIn(res.getInt("times_chk_in"));
		studentCourse.setUpdatedTimestamp(res.getInt("ts_update"));
		return studentCourse;
	}

	protected CheckIn populateCheckIn(ResultSet res) throws SQLException {
		CheckIn checkIn = new CheckIn();
		checkIn.setCheckinId(res.getInt("checkinid"));
		checkIn.setStudentId(res.getInt("studentid"));
		checkIn.setCourseId(res.getInt("courseid"));
		checkIn.setLatitude(res.getFloat("latitude"));
		checkIn.setLongitude(res.getFloat("longitude"));
		checkIn.setUpdatedTimestamp(res.getString("ts_update"));
		return checkIn;
	}

	protected InstructorCourse populateInstructorCourses(ResultSet res) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
