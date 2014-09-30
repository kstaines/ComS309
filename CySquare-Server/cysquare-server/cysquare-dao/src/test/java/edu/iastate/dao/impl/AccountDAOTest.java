package edu.iastate.dao.impl;

import static org.junit.Assert.*;

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
		
		deleteData();
		user = accountDao.getAccountInfo("use");
		assertEquals(null, user.getUsername());
		assertEquals(null, user.getPassword());
		
	}
	
	private void createData() {
		accountDao.createUserAccount("use", "pass");
	}
	
	private void deleteData() {
		accountDao.deleteUserAccount("use");
	}

}
