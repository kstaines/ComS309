package edu.iastate.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.UserAccount;

public class ResetPageServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private StudentCourseDAO studentCourseDao;
	
	@InjectMocks
	private ResetPageServlet resetPageServlet = new ResetPageServlet();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNoList() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		List<UserAccount> userList = new ArrayList<UserAccount> ();
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		resetPageServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testNullList () throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		List<UserAccount> userList = null;
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		resetPageServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
	}
	@Test
	public void testNoStudentUsers () throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		List<UserAccount> userList = new ArrayList<UserAccount> ();
		UserAccount adminUser = new UserAccount ();
		adminUser.setUserType("admin");
		userList.add(adminUser);
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		resetPageServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
	}
	@Test
	public void testResetPage () throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		List<UserAccount> userList = new ArrayList<UserAccount> ();
		UserAccount studentUser1 = new UserAccount ();
		studentUser1.setUserType("student");
		UserAccount studentUser2 = new UserAccount ();
		studentUser2.setUserType("student");
		UserAccount adminUser = new UserAccount ();
		adminUser.setUserType("admin");
		userList.add(studentUser1);
		userList.add(studentUser2);
		userList.add(adminUser);
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		resetPageServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
	}
	

}
