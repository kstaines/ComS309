package edu.iastate.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.Friend;
import edu.iastate.literals.DAOLiterals;

public class FriendDAO extends DatabaseAccess {

	public List<Friend> getFriendList(Integer studentId) {
		
		List<Friend> friends = new ArrayList<Friend>();
		
		try {
			Connection conn = makeConnection();
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_FRIENDS + " WHERE studentid=\"" + studentId + "\" OR friendid=\"" + studentId + "\";");
			if(res.next()) {
				Friend friend = new Friend();
				friend.setStudentId(res.getInt("studentid"));
				friend.setFriendId(res.getInt("friendid"));
				friend.setApproved(res.getString("approved"));
				friend.setUpdatedTimestamp(res.getString("ts_update"));
				friends.add(friend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	public void approveFriendship(Integer studentId, Integer friendId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			//Update friendship with an approval string of "Y".
			st.executeUpdate("UPDATE " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_FRIENDS + " SET `approved`='Y' WHERE studentid=\"" + studentId + "\" AND friendid=\"" + friendId + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createFriendship(Integer studentId, Integer friendId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_FRIENDS + " (`studentid`, `friendid`) VALUES ('" + studentId + "', '" + friendId + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFriendship(Integer studentId, Integer friendId) {
		Connection conn;
		try {
			conn = makeConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM " + DAOLiterals.MYSQL_DB_NAME + "." + DAOLiterals.TABLE_FRIENDS + " WHERE studentid='" + studentId + "' AND friendid='" + friendId + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
