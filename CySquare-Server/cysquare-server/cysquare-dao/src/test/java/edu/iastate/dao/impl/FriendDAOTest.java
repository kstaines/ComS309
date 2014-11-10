package edu.iastate.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.Friend;

public class FriendDAOTest {

	private FriendDAO friendDao;
	
	private Integer studentId = 0;
	private Integer friendId = 1;
	
	@Before
	public void setUp() {
		friendDao = new FriendDAO();
		deleteData();
		createData();
	}

	@After
	public void cleanUp() {
		deleteData();
	}

	@Test
	public void testGetTestCourse() {
		Friend testFriend = createFriend(studentId, friendId, "Y");
		List<Friend> friends = friendDao.getFriendList(studentId);
		assertNotNull(friends);
		assertFalse(friends.isEmpty());
		assertNotNull(friends.get(0));
		assertEquals("N", friends.get(0).getApproved());
		friendDao.approveFriendship(studentId, friendId);
		friends = friendDao.getFriendList(studentId);
		assertEquals("Y", friends.get(0).getApproved());
		assertEquals(testFriend.getStudentId(), friends.get(0).getStudentId());
		assertEquals(testFriend.getFriendId(), friends.get(0).getFriendId());
		assertEquals(testFriend.getApproved(), friends.get(0).getApproved());
	}

	private void createData() {
		friendDao.createFriendship(studentId, friendId);
		
	}
	
	private void deleteData() {
		friendDao.deleteFriendship(studentId, friendId);
	}
	
	private Friend createFriend(Integer studentId2, Integer friendId2, String approved) {
		Friend friend = new Friend();
		friend.setStudentId(studentId2);
		friend.setFriendId(friendId2);
		friend.setApproved(approved);
		return friend;
	}
}
