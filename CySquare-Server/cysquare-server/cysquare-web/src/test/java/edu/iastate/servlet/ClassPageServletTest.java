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
import edu.iastate.domain.StudentCourses;
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
		public void testDeleteCouse() throws IOException, JSONException {
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
			student.setUpdatedTimestamp(2);
			
			Course courseNew = new Course ();
			courseNew.setCourseId(154);
			courseNew.setName("COMS");
			courseNew.setDays("MWF");
			courseNew.setLocation("Coover");
			courseNew.setTime("9am");
			courseNew.setUpdatedTimestamp("2");
			courseNew.setSection("A");
			
			//List<StudentCourses> courseList = new ArrayList<StudentCourses> ();
			//courseList.add(0, student);
		
			//Mockito all the parameter passed to the servlet
			Mockito.when(request.getParameter("username")).thenReturn("user");
			Mockito.when(request.getParameter("editType")).thenReturn("delete");
			Mockito.when(request.getParameter("name")).thenReturn("COMS");
			Mockito.when(request.getParameter("section")).thenReturn("A");
			
			
			
			Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
			//Mockito.when(studentCourseDao.getCourses(userAccount.getUserId())).thenReturn(courseList);
			//Mockito.when(courseDao.getCourseInfoById(courseNew.getCourseId())).thenReturn(courseNew);
			Mockito.when(courseDao.getCourseInfoWithSection(courseNew.getName(), courseNew.getSection())).thenReturn(courseNew);
			Mockito.when(course.getCourseId()).thenReturn(154);
			
			Mockito.when(response.getWriter()).thenReturn(printWriter);
			
			classPage.doPost(request, response);
			
			//Mockito.verify(course).getCourseId();
			
			System.out.println(stringWriter.toString());
	}

}
