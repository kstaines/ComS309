package edu.iastate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.Friend;

public class FriendDAO extends DatabaseAccess {

	public List<Friend> getFriendList(Integer studentId) {
		// Query by both the studentid and the friendid when searching
		// for a list of friends
		return new ArrayList<Friend>();
	}
	
	public void approveFriendship(Integer studentId, Integer friendId) {
		//Set approved to Y
	}
	
	public void createFriendship(Integer studentId, Integer friendId) {
		
	}
	
	public void deleteFriendship(Integer studentId, Integer friendId) {
		
	}
}
