package edu.iastate.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.InstructorCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.InstructorCourse;
import edu.iastate.domain.UserAccount;
@WebServlet("/classList")

public class ClassListServlet extends HttpServlet{

	private static final long serialVersionUID = 8438444817401559341L;
	
	private CourseDAO courseDao = new CourseDAO ();
	private InstructorCourseDAO instructorDao = new InstructorCourseDAO ();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get all the request parameters
		String editType = request.getParameter("editType");
		
		//Set the response type
		response.setContentType("application/json");
		
		
		JSONObject list = new JSONObject ();
		
		//Check if the parameters are null or blank
		if(hasError(list, editType, "edit type", response)) return;
		
		
		//Get the corresponding courseList based upon if admin or instructor.
		if(editType.equalsIgnoreCase("instructor"))
		{
			//get the request parameter of username
			String instructor = request.getParameter("username");
			if(hasError(list, instructor, "user name", response)) return;
			AccountDAO accountDao = new AccountDAO ();
			UserAccount instructorAccount = accountDao.getAccountInfo(instructor);
			int instructorId = instructorAccount.getUserId();
			List<InstructorCourse> courseList1 = instructorDao.getCourses(instructorId);
			
			//Check if null or empty
			if(courseList1 == null || courseList1.isEmpty())
			{
				putError(list, "There is no class lists in the database for the instructor.", response);
				return;
			}
			//Loop through to get the course information that will be sent to client
			List<Course> courseList = new ArrayList<Course> ();
			for(int i = 0; i < courseList1.size(); i++)
			{
				Integer courseId = courseList1.get(i).getCourseId();
				Course course = courseDao.getCourseInfoById(courseId);
				courseList.add(course);
			}
			putList(list, courseList, response);
			return;
			
		}
		else if(editType.equalsIgnoreCase("admin"))
		{
			List<Course> courseList = courseDao.getAvailableCourseList();
			putList(list, courseList, response);
			return;
		}
		else
		{
			putError(list, "The edit type should be admin or instructor", response);
			return;
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
	 * Private Helper Method to determine if the string is null or blank
	 * @param object A JsonObject that is sent to the client
	 * @param toCheck A string that is being checked if null or blank
	 * @param toType A string word to identify what is null or blank in the message sent to the client
	 * @param response A Http Response to be sent to the client
	 */
	private boolean hasError(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck == null)
		{
			putError(object, "The " + toType + " is null.", response);
			return true;
		}
		else if(toCheck.equalsIgnoreCase(""))
		{
			putError(object, "The " + toType + " is blank.", response);
			return true;
		}
		return false;
	}
	
	/*
	 * Private Helper method to put the list in a JSONObject
	 * @param list A JSONObject that is sent to the client
	 * @param courseList A List of courses
	 * @param response A http servlet response to be sent to the client
	 */
	private void putList(JSONObject list, List<Course> courseList, HttpServletResponse response) throws IOException
	{
		if(courseList == null || courseList.isEmpty())
		{
			putError(list, "There is no class lists in the database.", response);
			return;
		}
		try {
			list.put("size", courseList.size());
		
			for(int i = 0; i < courseList.size(); i++)
			{
				Course course = courseList.get(i);
				
				list.put("Course" +(i+1), "Course name: " + course.getName() + " Section: " + course.getSection() +
							 " Location: " + course.getLocation() + " Days: " + course.getDays() + " Time: " + course.getTime());
			
			}
		
		list.write(response.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
