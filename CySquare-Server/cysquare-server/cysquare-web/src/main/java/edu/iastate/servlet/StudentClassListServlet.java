package edu.iastate.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.StudentCourse;

@WebServlet("/classStudent")
public class StudentClassListServlet extends HttpServlet {

	private static final long serialVersionUID = -4886600519550286378L;
	private StudentCourseDAO studentCourseDao = new StudentCourseDAO ();
	private AccountDAO account = new AccountDAO ();
	private CourseDAO courseDao = new CourseDAO ();
	private Course courseInfo = new Course ();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String user = request.getParameter("username");
		//get the list of all the courses the student user has
		List<StudentCourse> course = studentCourseDao.getCourses(account.getAccountInfo(user).getUserId());
		
		JSONObject object = new JSONObject ();
		try
		{
			//check if the user has any courses
			if(course.isEmpty())
			{
				putError(object, "You do not have any courses yet. Please add courses.", response);
			}
			object.put("size", course.size());
			for(int i = 0; i < course.size(); i++)
			{
				courseInfo = courseDao.getCourseInfoById(course.get(i).getCourseId());
				object.put("Course" + (i+1), "Course Name: " + courseInfo.getName() + " Section: " + courseInfo.getSection()
						   + " Location: " + courseInfo.getLocation() + " Time: " + courseInfo.getTime() + " Days: "
						   + courseInfo.getDays());
			}
			object.write(response.getWriter());			
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	private void putError(JSONObject object, String message, HttpServletResponse response)
	{
		try 
		{
			object.put("status", "error");
			object.put("error", message);
			try 
			{
				object.write(response.getWriter());
				return;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
	}
}
