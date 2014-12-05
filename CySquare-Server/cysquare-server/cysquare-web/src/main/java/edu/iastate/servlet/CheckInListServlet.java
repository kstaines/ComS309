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
import edu.iastate.dao.impl.CheckInDAO;
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.domain.CheckIn;
import edu.iastate.domain.Course;
import edu.iastate.domain.UserAccount;

@WebServlet("/checkInList")
public class CheckInListServlet extends HttpServlet {

	private static final long serialVersionUID = -2724038709471649446L;
	private CheckInDAO checkInDao = new CheckInDAO ();
	private CourseDAO courseDao = new CourseDAO ();
	private AccountDAO accountDao = new AccountDAO ();

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//Get request parameters
		String user = request.getParameter("username");
		String courseName = request.getParameter("classname");
		String section = request.getParameter("section");
		
		JSONObject object = new JSONObject ();
		
		//check if the parameters are null or blank
		if(hasError(object, user, "user name", response)) return;
		if(hasError(object, courseName, "class name", response)) return;
		if(hasError(object, section, "section", response)) return;
		
		//get the current check in list
		List<CheckIn> checkInList = checkInDao.getAllCurrentCheckIns();
		Course courseInfo = courseDao.getCourseInfoWithSection(courseName, section);
		//Check if courseInfo is null
		if(courseInfo == null)
		{
			putError(object, "The course is not found in the database", response);
			return;
		}
		
		int courseIdOriginal = courseInfo.getCourseId();
		
		//the size to be returned to the client
		int size = 0;
		
		 //check if checkInList is null or empty
		if(checkInList == null || checkInList.isEmpty())
		{
			putError(object, "There are no current checkin's. Please check back later.", response);
			return;
		}
		
		try 
		{
			
		
			for(int i = 0; i < checkInList.size(); i++)
			{
				//Get all the required info
				CheckIn checkIn = checkInList.get(i);
				int courseIdToCheck = checkIn.getCourseId();
				int studentId = checkIn.getStudentId();
				
				//If the course id's match then get all the checkins associated with that course
				if(courseIdToCheck == courseIdOriginal)
				{
					size = size + 1;
					UserAccount studentUser = accountDao.getAccountInfoById(studentId);
					object.put("Student " + size, "Username: " + studentUser.getUsername());	
				}
			}
			object.put("size", size);
			putTrue(object, response);
			return;
		}
		catch (JSONException e) {
			
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
	 * Private Helper Method to send a true message to the client.
	 * @param object A JSONObject type to be sent to the client
	 * @param response An HttpServletResponse to be sent to the client
	 */
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
