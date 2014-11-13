package edu.iastate.servlet;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;

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
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.UserAccount;

public class ClassPageServletTest {

		@Mock
		private AccountDAO accountDao;
		
		@Mock
		private StudentCourseDAO studentCourseDao;
		
		@Mock
		private CourseDAO courseDao;
		
		@Mock 
		private Course course;
		
		
		@InjectMocks
		private ClassPageServlet classPage = new ClassPageServlet();
		
		
		@Before
		public void setUp() {
			MockitoAnnotations.initMocks(this);
		}
		
		@Test
		public void testDeleteCourse() throws IOException, JSONException {
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
			
			Course courseNew = new Course ();
			courseNew.setCourseId(154);
			courseNew.setName("COMS");
			courseNew.setDays("MWF");
			courseNew.setLocation("Coover");
			courseNew.setTime("9am");
			courseNew.setUpdatedTimestamp("2");
			courseNew.setSection("A");
			

			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn("COMS");
			Mockito.when(request.getParameter("section")).thenReturn("A");
			
			
			
			Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
			Mockito.when(courseDao.getCourseInfoWithSection(courseNew.getName(), courseNew.getSection())).thenReturn(courseNew);
			Mockito.when(course.getCourseId()).thenReturn(154);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
	}
		
		@Test
		public void testAddCourse() throws IOException, JSONException
		{
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
			
			Course courseNew = new Course ();
			courseNew.setCourseId(154);
			courseNew.setName("COMS");
			courseNew.setDays("MWF");
			courseNew.setLocation("Coover");
			courseNew.setTime("9am");
			courseNew.setSection("A");
			courseNew.setUpdatedTimestamp("2");

			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("add");
			Mockito.when(request.getParameter("name")).thenReturn("COMS");
			Mockito.when(request.getParameter("section")).thenReturn("A");
			
			
			
			Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
			Mockito.when(courseDao.getCourseInfoWithSection(courseNew.getName(), courseNew.getSection())).thenReturn(courseNew);
			Mockito.when(course.getCourseId()).thenReturn(154);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
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
			
			classPage.doPost(request, response);
			
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
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}
		@Test
		public void testEditTypeNull() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn(null);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}
		@Test
		public void testEditTypeBlank() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("");
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}

		@Test
		public void testNameNull() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn(null);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}
		@Test
		public void testNameBlank() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn("");
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}

		@Test
		public void testSectionNull() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn("COMS");
			Mockito.when(request.getParameter("section")).thenReturn(null);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}
		@Test
		public void testSectionBlank() throws IOException, JSONException
		{
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
			
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn("COMS");
			Mockito.when(request.getParameter("section")).thenReturn("");
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			System.out.println(stringWriter.toString());
			
		}


}
