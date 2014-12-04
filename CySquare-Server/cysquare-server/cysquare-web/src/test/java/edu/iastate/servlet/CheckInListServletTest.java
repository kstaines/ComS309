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

import edu.iastate.dao.impl.CheckInDAO;
import edu.iastate.domain.CheckIn;

public class CheckInListServletTest {

	@Mock
	private CheckInDAO checkInDao;
	
	@InjectMocks
	private CheckInListServlet checkInListServlet = new CheckInListServlet();
	
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
		
		checkInListServlet.doPost(request, response);
		
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
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testClassnameNull() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn(null);
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testClassnameBlank() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("");
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testSectionNull() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("COMS");
		Mockito.when(request.getParameter("section")).thenReturn(null);
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testSectionBlank() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("COMS");
		Mockito.when(request.getParameter("section")).thenReturn("");
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testNoList() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("COMS");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		List<CheckIn> checkInList = new ArrayList<CheckIn> ();
		
		Mockito.when(checkInDao.getAllCurrentCheckIns()).thenReturn(checkInList);
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testNullList() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("COMS");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		List<CheckIn> checkInList = null;
		
		Mockito.when(checkInDao.getAllCurrentCheckIns()).thenReturn(checkInList);
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkInListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}

}
