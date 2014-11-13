package edu.iastate.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.FriendDAO;
import edu.iastate.domain.Friend;
import edu.iastate.domain.UserAccount;



public class FriendsPageServletTest {

	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private FriendDAO friendDao;
	
	
	@InjectMocks
	private FriendsPageServlet friendsPage = new FriendsPageServlet();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testInitFriendsPage() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		JSONObject obj = new JSONObject();
		obj.put("username", "user");
		
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("user");
		userAccount.setTotalPts(10);
		userAccount.setUserId(123);
		userAccount.setApproved("y");
		userAccount.setPassword("password");
		userAccount.setUpdatedTimestamp("2");
		userAccount.setUserType("student");
		
		UserAccount user = new UserAccount ();
		user.setUsername("username");
		user.setUserId(54);
		
		UserAccount usertwo = new UserAccount ();
		usertwo.setUsername("usertwo");
		user.setUserId(145);
		
		Friend friend = new Friend ();
		friend.setApproved("y");
		friend.setFriendId(54);
		friend.setStudentId(123);
		
		Friend friendtwo = new Friend ();
		friendtwo.setApproved("n");
		friendtwo.setFriendId(145);
		friendtwo.setStudentId(87);
		
		List<Friend> friendList = new ArrayList <Friend>();
		friendList.add(friend);
		friendList.add(friendtwo);

		

		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
		Mockito.when(friendDao.getFriendList(userAccount.getUserId())).thenReturn(friendList);
		Mockito.when(accountDao.getAccountInfoById(54)).thenReturn(user);
		Mockito.when(accountDao.getAccountInfoById(145)).thenReturn(usertwo);
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		friendsPage.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testUserNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn(null);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		friendsPage.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testUserBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("");
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		friendsPage.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	
}
