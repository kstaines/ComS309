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
		
		JSONObject object = new JSONObject ();
		if(isNull(object, user, "user name", response)) return;
		if(isBlank(object, user, "user name", response)) return;
		//get the list of all the courses the student user has
		List<StudentCourse> course = studentCourseDao.getCourses(account.getAccountInfo(user).getUserId());
		
		
		try
		{
			//check if the user has any courses
			if(course == null || course.isEmpty())
			{
				putError(object, "You do not have any courses yet. Please add courses.", response);
				return;
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
	/*
	 * Private Helper Method to send an error message to the client
	 * @param object A JSONObject type to be sent to the client
	 * @param message A String that contains the message to be sent to the client
	 * @param response An HttpServletResponse to be sent to the client
	 */
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
	/*
	 * Private Helper Method that will check if it is null and send a message back to the client
	 * @param object A JSON object to be sent to the client
	 * @param toCheck A String that needs to be checked if it is null
	 * @param toType A String word to help identify what is null
	 * @param response A HttpServletResponse sent to the client
	 */
	private boolean isNull(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck == null)
		{
			putError(object, "The " + toType + " is null.", response);
			return true;
		}
		return false;
	}
	
	/*
	 * Private Helper Method that will check if it is blank and send a message back to the client
	 * @param object A JSON object to be sent to the client
	 * @param toCheck A String that needs to be checked if it is blank
	 * @param toType A String word to help identify what is blank
	 * @param response A HttpServletResponse sent to the client
	 */
	private boolean isBlank(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck.equalsIgnoreCase(""))
		{
			putError(object, "The " + toType + " is blank.", response);
			return true;
		}
		return false;
	}

}
