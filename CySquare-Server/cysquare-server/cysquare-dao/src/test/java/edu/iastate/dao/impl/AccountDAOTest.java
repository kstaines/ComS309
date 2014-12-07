package edu.iastate.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.UserAccount;

public class AccountDAOTest {

	private AccountDAO accountDao;
	
	@Before
	public void setUp() {
		accountDao = new AccountDAO();
		deleteData();
		createData();
	}
	
	@After
	public void cleanUp() {
		deleteData();
	}

	@Test
	public void testGetFirstTestUser() {
		UserAccount user = accountDao.getAccountInfo("use");
		assertEquals("use", user.getUsername());
		assertEquals("pass", user.getPassword());
		assertEquals("STUDENT", user.getUserType());
		assertNotSame(Integer.valueOf("1"), user.getTotalPts());
	}
	
	@Test
	public void testCreateDeleteData() {
		UserAccount user = new UserAccount();
		deleteData();
		user = accountDao.getAccountInfo("use");
		assertEquals(null, user.getUsername());
		assertEquals(null, user.getPassword());
		
		createData();
		user = accountDao.getAccountInfo("use");
		assertEquals("use", user.getUsername());
		assertEquals("pass", user.getPassword());
		assertEquals(Integer.valueOf("0"), user.getTotalPts());
		
		deleteData();
		user = accountDao.getAccountInfo("use");
		assertEquals(null, user.getUsername());
		assertEquals(null, user.getPassword());
		
	}
	
	@Test
	public void testGetUnapprovedUsers() {
		accountDao.deleteUserAccount("use1");
		accountDao.deleteUserAccount("use2");
		accountDao.deleteUserAccount("use3");
		accountDao.createUserAccount("use1", "pass", "INSTRUCTOR");
		accountDao.createUserAccount("use2", "pass", "INSTRUCTOR");
		accountDao.createUserAccount("use3", "pass", "INSTRUCTOR");
		List<UserAccount> users = accountDao.getUnapprovedUsers();
		assertEquals(false, users.isEmpty());
		System.out.println(users.size());
		accountDao.deleteUserAccount("use1");
		accountDao.deleteUserAccount("use2");
		accountDao.deleteUserAccount("use3");
	}
	
	@Test
	public void testGetAllUsers() {
		List<UserAccount> users = accountDao.getAllUsers();
		assertEquals(false, users.isEmpty());
		System.out.println(users.size());
	}
	
	@Test
	public void testUpdatePoints() {
		UserAccount user = accountDao.getAccountInfo("use");
		assertEquals(Integer.valueOf(0), user.getTotalPts());
		accountDao.updatePointValue("use", 10);
		user = accountDao.getAccountInfo("use");
		assertEquals(Integer.valueOf(10), user.getTotalPts());
	}
	
	@Test
	public void testUpdatePointsById() {
		UserAccount user = accountDao.getAccountInfo("use");
		assertEquals(Integer.valueOf(0), user.getTotalPts());
		accountDao.updatePointValueById(user.getUserId(), 10);
		user = accountDao.getAccountInfoById(user.getUserId());
		assertEquals(Integer.valueOf(10), user.getTotalPts());
	}
	
	private void createData() {
		accountDao.createUserAccount("use", "pass", "STUDENT");
	}
	
	private void deleteData() {
		accountDao.deleteUserAccount("use");
		accountDao.deleteUserAccount("use1");
		accountDao.deleteUserAccount("use2");
		accountDao.deleteUserAccount("use3");
	}

}
