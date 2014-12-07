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
import edu.iastate.dao.impl.InstructorCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.InstructorCourse;
import edu.iastate.domain.UserAccount;


public class ClassListServletTest {
	
	@Mock
	private CourseDAO courseDao;
	
	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private InstructorCourseDAO instructorDao;
	
	
	
	@InjectMocks
	private ClassListServlet classList = new ClassListServlet ();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testClassListAdmin() throws IOException, JSONException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Course course = new Course ();
		course.setCourseId(123);
		course.setDays("MWF");
		course.setLocation("Coover 2014");
		course.setName("COMS 309");
		course.setSection("A");
		course.setTime("9AM");
		
		Course courseone = new Course ();
		courseone.setCourseId(145);
		courseone.setDays("TR");
		courseone.setLocation("Hoover 32");
		courseone.setName("COMS 311");
		courseone.setSection("D");
		courseone.setTime("10AM");
		
		
		List<Course> courseList = new ArrayList<Course> ();
		courseList.add(course);
		courseList.add(courseone);
		
		Mockito.when(courseDao.getAvailableCourseList()).thenReturn(courseList);
		Mockito.when(request.getParameter("editType")).thenReturn("admin");
	
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		classList.doPost(request, response);
				
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testNoList() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		List<Course> courseList = new ArrayList<Course> ();
		
		Mockito.when(courseDao.getAvailableCourseList()).thenReturn(courseList);
		Mockito.when(request.getParameter("editType")).thenReturn("admin");
	
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		classList.doPost(request, response);
				
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testNullList() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		List<Course> courseList = null;
		
		Mockito.when(courseDao.getAvailableCourseList()).thenReturn(courseList);
		Mockito.when(request.getParameter("editType")).thenReturn("admin");
	
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		classList.doPost(request, response);
				
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testClassListInstructor() throws IOException, JSONException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		Course course = new Course ();
		course.setCourseId(123);
		course.setDays("MWF");
		course.setLocation("Coover 2014");
		course.setName("COMS 309");
		course.setSection("A");
		course.setTime("9AM");
		
		Course courseone = new Course ();
		courseone.setCourseId(145);
		courseone.setDays("TR");
		courseone.setLocation("Hoover 32");
		courseone.setName("COMS 311");
		courseone.setSection("D");
		courseone.setTime("10AM");
		
		
		List<Course> courseList = new ArrayList<Course> ();
		courseList.add(course);
		courseList.add(courseone);
		
		UserAccount accountInfo = new UserAccount ();
		accountInfo.setUserId(145);
		
		List<InstructorCourse> instructorList = new ArrayList<InstructorCourse> ();
		InstructorCourse instructorCourseInfo = new InstructorCourse ();
		instructorCourseInfo.setInstructorId(145);
		instructorCourseInfo.setCourseId(123);
		InstructorCourse instructorCourseInfo2 = new InstructorCourse ();
		instructorCourseInfo2.setCourseId(145);
		instructorCourseInfo2.setInstructorId(145);
		instructorList.add(instructorCourseInfo);
		instructorList.add(instructorCourseInfo2);
		
		//Mockito.when(courseDao.getAvailableCourseList()).thenReturn(courseList);
		Mockito.when(request.getParameter("editType")).thenReturn("instructor");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(accountInfo);
		Mockito.when(instructorDao.getCourses(accountInfo.getUserId())).thenReturn(instructorList);
		Mockito.when(courseDao.getCourseInfoById(123)).thenReturn(course);
		Mockito.when(courseDao.getCourseInfoById(145)).thenReturn(courseone);
	
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		classList.doPost(request, response);
				
		System.out.println(stringWriter.toString());
		
	}
	

}
