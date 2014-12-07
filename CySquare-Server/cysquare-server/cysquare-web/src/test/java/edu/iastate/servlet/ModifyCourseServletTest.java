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
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.UserAccount;



/**
 * Test for the ModifyCourseServlet
 * @author Brenda Lopez
 *
 */
public class ModifyCourseServletTest {

	@Mock
	private CourseDAO courseDao;
	
	@Mock
	private AccountDAO accountDao;
	
	@Mock 
	private StudentCourseDAO studentDao;
	
	@Mock
	private InstructorCourseDAO instructorDao;

	
	@InjectMocks
	private ModifyCourseServlet modifyCourseServlet = new ModifyCourseServlet();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCourseNameNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn(null);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testCourseNameBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testEditTypeNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		
		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn(null);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testEditTypeBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testWrongEditType() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("remove");
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testDeleteMethod() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("delete");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		UserAccount userAccount = new UserAccount ();
		userAccount.setUserType("instructor");
		userAccount.setUserId(3);
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userAccount);
		Course courseInfo = new Course ();
		courseInfo.setCourseId(6);
		Mockito.when(courseDao.getCourseInfoWithSection("COMS", "A")).thenReturn(courseInfo);
		
		
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodLocationNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn(null);
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodLocationBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("");
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodTimeNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn(null);
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}

	@Test
	public void testAddMethodTimeBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("");
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodSectionNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn(null);
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodSectionBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn("");
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testAddMethodDaysNull() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("days")).thenReturn(null);
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodDaysBlank() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("days")).thenReturn("");
		
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	
	@Test
	public void testAddMethodErrorFoundInDatabase() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("days")).thenReturn("MW");
		
		List<Course> currentList = new ArrayList <Course> ();
		Course course1 = new Course ();
		course1.setName("COMS");
		course1.setSection("A");
		currentList.add(course1);	
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Mockito.when(courseDao.getAvailableCourseList()).thenReturn(currentList);
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
	@Test
	public void testAddMethodInstructor() throws IOException, JSONException
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Mockito.when(request.getParameter("coursename")).thenReturn("COMS");
		Mockito.when(request.getParameter("editType")).thenReturn("add");
		Mockito.when(request.getParameter("username")).thenReturn("user");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("location")).thenReturn("Hoover");
		Mockito.when(request.getParameter("time")).thenReturn("0900");
		Mockito.when(request.getParameter("section")).thenReturn("A");
		Mockito.when(request.getParameter("days")).thenReturn("MW");
		
		List<Course> currentList = new ArrayList <Course> ();
		Course course1 = new Course ();
		course1.setName("COMS");
		course1.setSection("B");
		currentList.add(course1);		
		
		UserAccount userInfo = new UserAccount ();
		userInfo.setUsername("user");
		userInfo.setUserId(145);
		userInfo.setUserType("instructor");
		
		Course courseInfo = new Course ();
		courseInfo.setCourseId(6);
		
		Mockito.when(courseDao.getAvailableCourseList()).thenReturn(currentList);
		Mockito.when(accountDao.getAccountInfo("user")).thenReturn(userInfo);
		Mockito.when(courseDao.getCourseInfoWithSection("COMS", "A")).thenReturn(courseInfo);
		
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		modifyCourseServlet.doPost(request, response);
		
		System.out.println(stringWriter.toString());
		
	}
}
