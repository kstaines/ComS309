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
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.StudentCourses;
import edu.iastate.domain.UserAccount;

public class ProfilePageServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private StudentCourseDAO courses;
	
	@Mock
	private CourseDAO courseInfo;
	
	@InjectMocks
	private ProfilePageServlet profile = new ProfilePageServlet ();
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testInitialProfile() throws IOException, JSONException {
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
		
		StudentCourses student = new StudentCourses ();
		student.setStudentId(123);
		student.setCourseId(154);
		student.setPoints(5);
		student.setUpdatedTimestamp(2);
		
		Course course = new Course ();
		course.setCourseId(154);
		course.setName("COMS");
		course.setDays("MWF");
		course.setLocation("Coover");
		course.setTime("9am");
		course.setUpdatedTimestamp("2");
		
		List<StudentCourses> courseList = new ArrayList<StudentCourses> ();
		courseList.add(0, student);
	
		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
		Mockito.when(courses.getCourses(userAccount.getUserId())).thenReturn(courseList);
		Mockito.when(courseInfo.getCourseInfoById(course.getCourseId())).thenReturn(course);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		profile.doPost(request, response);
		
		Mockito.verify(courses).getCourses(123);
		
		System.out.println(stringWriter.toString());
	}
	
	@Test
	public void testNoClass() throws IOException, JSONException
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
		
		StudentCourses student = new StudentCourses ();
		student.setStudentId(123);
		student.setCourseId(154);
		student.setPoints(5);
		student.setUpdatedTimestamp(2);

		
		List<StudentCourses> courseList = new ArrayList<StudentCourses> ();
	
		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
		Mockito.when(courses.getCourses(userAccount.getUserId())).thenReturn(courseList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		profile.doPost(request, response);
		
		Mockito.verify(courses).getCourses(123);
		
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
		
		profile.doPost(request, response);
		
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
		
		profile.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}


}
