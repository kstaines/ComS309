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
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.StudentCourse;
import edu.iastate.domain.UserAccount;

public class StudentClassListServletTest {


	@Mock
	private CourseDAO courseDao;
	@Mock
	private StudentCourseDAO studentCourseDao;
	@Mock
	private AccountDAO account;

	
	@InjectMocks
	private StudentClassListServlet studentClassListServlet = new StudentClassListServlet();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUserNameNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("username")).thenReturn(null);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		studentClassListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testUserNameBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("username")).thenReturn("");
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		studentClassListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testNoCourses() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		String user = "user";
		List<StudentCourse> course = new ArrayList<StudentCourse> ();
		UserAccount userAccount = new UserAccount ();
		userAccount.setUserId(145);

		Mockito.when(account.getAccountInfo(user)).thenReturn(userAccount);
		Mockito.when(studentCourseDao.getCourses(account.getAccountInfo(user).getUserId())).thenReturn(course);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		studentClassListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testNullCourses() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		String user = "user";
		List<StudentCourse> course = null;
		UserAccount userAccount = new UserAccount ();
		userAccount.setUserId(145);

		Mockito.when(account.getAccountInfo(user)).thenReturn(userAccount);
		Mockito.when(studentCourseDao.getCourses(account.getAccountInfo(user).getUserId())).thenReturn(course);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		studentClassListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testStudentCourseList() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("username")).thenReturn("user");
		
		String user = "user";
		List<StudentCourse> course = new ArrayList<StudentCourse> ();
		StudentCourse studentCourse = new StudentCourse ();
		studentCourse.setCourseId(155);
		studentCourse.setPoints(4);
		studentCourse.setStudentId(145);
		course.add(studentCourse);
		
		UserAccount userAccount = new UserAccount ();
		userAccount.setUserId(145);
		
		Course courseInfo = new Course ();
		courseInfo.setDays("MWF");
		courseInfo.setCourseId(155);
		courseInfo.setLocation("Coover");
		courseInfo.setName("COMS");
		courseInfo.setSection("A");
		courseInfo.setTime("0900");
		
		
		Mockito.when(courseDao.getCourseInfoById(studentCourse.getCourseId())).thenReturn(courseInfo);
		Mockito.when(account.getAccountInfo(user)).thenReturn(userAccount);
		Mockito.when(studentCourseDao.getCourses(account.getAccountInfo(user).getUserId())).thenReturn(course);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		studentClassListServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}

}
