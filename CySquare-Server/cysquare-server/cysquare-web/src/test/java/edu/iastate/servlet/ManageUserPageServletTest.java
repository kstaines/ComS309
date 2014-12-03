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

public class ManageUserPageServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@InjectMocks
	private ManageUserPageServlet manageUser = new ManageUserPageServlet();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUsernameNull() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn(null);
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testUsernameBlank() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testEditTypeNull() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("editType")).thenReturn(null);
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testEditTypeBlank() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("editType")).thenReturn("");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testEditTypeWrong() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("editType")).thenReturn("disapprove");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testDeleteMethod() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("editType")).thenReturn("delEte");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testApproveMethod() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("editType")).thenReturn("approve");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		manageUser.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	

}
