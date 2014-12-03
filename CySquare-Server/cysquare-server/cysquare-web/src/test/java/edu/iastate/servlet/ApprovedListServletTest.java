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

public class ApprovedListServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private StudentCourseDAO studentCourseDao;
	
	@InjectMocks
	private ApprovedListServlet approvedList = new ApprovedListServlet();
	
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
		
		approvedList.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testNullList() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		
		List<UserAccount> userList = null;
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		approvedList.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testNotApprovedList() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		
		List<UserAccount> userList = new ArrayList<UserAccount> ();
		UserAccount user1 = new UserAccount ();
		UserAccount user2 = new UserAccount ();
		UserAccount user3 = new UserAccount ();
		user1.setApproved("y");
		user1.setUsername("Instructor 1");
		user2.setApproved("n");
		user2.setUsername("Instructor 2");
		user3.setApproved("y");
		user3.setUsername("Instructor 3");
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		
		Mockito.when(accountDao.getAllUsers()).thenReturn(userList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		approvedList.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}




}
