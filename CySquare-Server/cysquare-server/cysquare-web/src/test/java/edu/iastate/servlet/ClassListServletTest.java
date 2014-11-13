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

import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.domain.Course;


public class ClassListServletTest {
	
	@Mock
	private CourseDAO courseDao;
	
	
	
	@InjectMocks
	private ClassListServlet classList = new ClassListServlet ();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testClassList() throws IOException, JSONException {
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
	
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		classList.doPost(request, response);
				
		System.out.println(stringWriter.toString());
		
	}
	

}
