package edu.iastate.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

//import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.CourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.StudentCourse;
import edu.iastate.domain.UserAccount;

@WebServlet("/checkIn")
public class CheckInServlet extends HttpServlet
{
	private static final long serialVersionUID = 943348396279963269L;
	
	private AccountDAO account_dao = new AccountDAO();
	private StudentCourseDAO student_dao = new StudentCourseDAO ();
	private CourseDAO course_dao = new CourseDAO ();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get the string from the client side
		String username_string = request.getParameter("username");
		String classname = request.getParameter("classname");
		String section = request.getParameter("section");
		//float latitude = request.getParameter("float");
		
		//Set the response type
		response.setContentType("application/json");
		
		JSONObject object = new JSONObject ();
		
		//check if username_string and classname is null or blank
		if(isNull(object, username_string, "username", response)) return;
		if(isBlank(object, username_string, "username", response)) return;
		
		if(isNull(object, classname,"class name", response)) return;
		if(isBlank(object, classname, "class name", response)) return;
		
		if(isNull(object, section, "section", response)) return;
		if(isBlank(object, section, "section", response)) return;
		
		//Get the class list which has the location, which will identify if the user is checking into class, or studying
		//get pojo_class_info from the DAO 
		
		//check in each pojo_class_info to find the location
		//if the pojo_location is the same as the current location
		//then check into class
		//else check into studying
		//return if checked into class or studying
		
		
		
		//Send the response that it was successful
		//Update points for the user and check the user in
		UserAccount userAccount = account_dao.getAccountInfo(username_string);
		Integer points = userAccount.getTotalPts();
		
		//Increase the total points by one
		points = points + 1;
		
		//Update the total points by user total and then by class they choose
		userAccount.setTotalPts(points);
		
		//get correlation list
		List<StudentCourse> correlationList = student_dao.getCourses(userAccount.getUserId());
		
		//check if the student has any courses
		if(correlationList == null || correlationList.isEmpty())
		{
			putError(object, "You do not have any courses yet. Please add courses.", response);
			return;
		}
		//Have a check to see if they actually have that course in their list
		boolean found = false;
		
		//Get course info
		Course courseInfo = course_dao.getCourseInfoWithSection(classname, section);
		//Go through the list to update the points 
		for(int i = 0; i < correlationList.size(); i++)
		{
			Integer courseId = correlationList.get(i).getCourseId();
			if(courseId.equals(courseInfo.getCourseId()))
			{
				found = true;
				Integer coursePoints = correlationList.get(i).getPoints();
				coursePoints = coursePoints + 1;
				correlationList.get(i).setPoints(coursePoints);
				return;
			
			}
			
		}
		//if it was found then the points were updated otherwise send a message stating that it was not found on the users list
		if(found)
		{
			putTrue(object, response);
			return;
		}
		else
		{
			putError(object, "There was no class found in the users list.", response);
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
