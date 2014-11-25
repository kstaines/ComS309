package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.CourseDAO;

@WebServlet("/modifyCourse")
public class ModifyCourseServlet extends HttpServlet {

	
	private static final long serialVersionUID = 963016886349131227L;
	
	private CourseDAO courseDao = new CourseDAO ();
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//Get the always be sent parameter from the client
		String courseName = request.getParameter("coursename");
		String editType = request.getParameter("editType");
		
		JSONObject object = new JSONObject ();
		
		//Check if the parameters are null or blank
		if(isNull(object, courseName, "course name", response)) return;
		if(isBlank(object, courseName, "course name", response)) return;
		if(isNull(object, editType, "edit type", response)) return;
		if(isBlank(object, editType, "edit type", response)) return;
		
		//if edit type is not delete or add the send an error message
		if(!(editType.equalsIgnoreCase("delete") || editType.equalsIgnoreCase("add")))
		{
			putError(object, "The edit type should be either delete or add", response);
			return;
		}
		
		
		//If the edit type is delete then delete the course
		if(editType.equalsIgnoreCase("delete"))
		{
			courseDao.deleteCourse(courseName);
			putTrue(object, response);
			return;
		}
		else
		{
			//Get the rest of the parameters and check if null or blank
			String location = request.getParameter("location");
			String time = request.getParameter("time");
			String section = request.getParameter("section");
			String days = request.getParameter("days");
			
			if(isNull(object, location, "location", response)) return;
			if(isBlank(object, location, "location", response)) return;
			if(isNull(object, time, "time", response)) return;
			if(isBlank(object, time, "time", response)) return;
			if(isNull(object, section, "section", response)) return;
			if(isBlank(object, section, "section", response)) return;
			if(isNull(object, days, "days", response)) return;
			if(isBlank(object, days, "days", response)) return;
			
			//Awaiting for the course dao to be changed to include the section
			courseDao.createCourse(courseName, location, time, days);
			putTrue(object, response);
			return;
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
	
	private void putTrue(JSONObject object, HttpServletResponse response)
	{
		try
		{
			object.put("status", true);
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
	
	private boolean isNull(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck == null)
		{
			putError(object, "The " + toType + " is null.", response);
			return true;
		}
		return false;
	}
	
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
