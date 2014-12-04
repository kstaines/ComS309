package edu.iastate.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.domain.Course;
@WebServlet("/classList")

public class ClassListServlet extends HttpServlet{

	private static final long serialVersionUID = 8438444817401559341L;
	
	private CourseDAO courseDao = new CourseDAO ();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get all the request parameters
		String editType = request.getParameter("editType");
		
		JSONObject list = new JSONObject ();
		
		//Check if the parameters are null or blank
		if(hasError(list, editType, "edit type", response)) return;
		
		//Get the corresponding courseList based upon if admin or instructor.
		if(editType.equalsIgnoreCase("instructor"))
		{
			
		}
		List<Course> courseList = courseDao.getAvailableCourseList();
		
		try
		{
			if(courseList.isEmpty())
			{
				putError(list, "There is no class lists in the database.", response);
				return;
			}
			list.put("size", courseList.size());
			for(int i = 0; i < courseList.size(); i++)
			{
				Course course = courseList.get(i);
				list.put("Course" +(i+1), "Course name: " + course.getName() + " Section: " + course.getSection() +
							 " Location: " + course.getLocation() + " Days: " + course.getDays() + " Time: " + course.getTime());
			}
			list.write(response.getWriter());
			return;
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

}
