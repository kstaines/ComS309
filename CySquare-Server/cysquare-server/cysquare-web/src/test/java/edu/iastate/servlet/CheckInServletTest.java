package edu.iastate.servlet;

import static org.junit.Assert.*;

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
import edu.iastate.dao.impl.FriendDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.StudentCourse;
import edu.iastate.domain.UserAccount;

public class CheckInServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@Mock
	private StudentCourseDAO studentDao;
	
	@Mock 
	private CourseDAO courseDao;
	
	@Mock
	private UserAccount userAccount;
	
	
	@InjectMocks
	private CheckInServlet checkIn = new CheckInServlet ();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCourseNotThere() throws IOException, JSONException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		//UserAccount userAccount = account_dao.getAccountInfo(username_string);
		UserAccount userAccount1 = new UserAccount ();
		userAccount1.setTotalPts(2);
		userAccount1.setUserId(15);
		
		//List<StudentCourse> correlationList = student_dao.getCourses(userAccount.getUserId());
		List<StudentCourse> correlationList = new ArrayList <StudentCourse> ();
		StudentCourse studentCourse = new StudentCourse ();
		studentCourse.setCourseId(54);
		correlationList.add(studentCourse);
		
		//Course courseInfo = course_dao.getCourseInfoWithSection(classname, section);
		Course course = new Course ();
		course.setCourseId(45);
		course.setSection("A");
		course.setName("COMS");
		
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("classname")).thenReturn("COMS");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount1);
		Mockito.when(userAccount.getTotalPts()).thenReturn(userAccount1.getTotalPts());
		Mockito.when(studentDao.getCourses(15)).thenReturn(correlationList);
		Mockito.when(courseDao.getCourseInfoWithSection("COMS", "A")).thenReturn(course);
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		checkIn.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}



}
