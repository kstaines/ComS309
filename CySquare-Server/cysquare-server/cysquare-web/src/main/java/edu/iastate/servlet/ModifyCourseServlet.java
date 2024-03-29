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
import edu.iastate.dao.impl.InstructorCourseDAO;
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.Course;
import edu.iastate.domain.UserAccount;

@WebServlet("/modifyCourse")
public class ModifyCourseServlet extends HttpServlet {

	
	private static final long serialVersionUID = 963016886349131227L;
	
	private CourseDAO courseDao = new CourseDAO ();
	private AccountDAO accountDao = new AccountDAO ();
	private StudentCourseDAO studentCourseDao = new StudentCourseDAO ();
	private InstructorCourseDAO instructorCourseDao = new InstructorCourseDAO ();
	/**
	 * Returns a HTTP response back to the client as a JSON object with the status of either true or an error message.
	 * This method receives the request from the client and processes
	 * the course information given by the client, which is checked and given to the DAO (Database Access Object)
	 * to be added to the database.
	 * 
	 * @param request An HTTP request received by the client to be processed
	 * @param response An HTTP response to send to the client as a JSON object with the status of the process	
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//Get the always be sent parameter from the client
		String courseName = request.getParameter("coursename");
		String editType = request.getParameter("editType");
		String user = request.getParameter("username");
		String section = request.getParameter("section");
		
		//Set the response type
		response.setContentType("application/json");
		
		JSONObject object = new JSONObject ();
		
		//Check if the parameters are null or blank
		if(isNull(object, courseName, "course name", response)) return;
		if(isBlank(object, courseName, "course name", response)) return;
		if(isNull(object, editType, "edit type", response)) return;
		if(isBlank(object, editType, "edit type", response)) return;
		if(isNull(object, user, "user name", response)) return;
		if(isBlank(object, user, "user name", response)) return;
		if(isNull(object, section, "section", response)) return;
		if(isBlank(object, section, "section", response)) return;
		
		//if edit type is not delete or add the send an error message
		if(!(editType.equalsIgnoreCase("delete") || editType.equalsIgnoreCase("add")))
		{
			putError(object, "The edit type should be either delete or add", response);
			return;
		}
		
		//Get the user account info
		UserAccount userAccount = accountDao.getAccountInfo(user);
		//put error if can not find the user in the database
		if(userAccount == null)
		{
			putError(object, "The user can not be found in the database.", response);
			return;
		}
		
		
		
		
		String userType = userAccount.getUserType();
		int userId = userAccount.getUserId();
		
		//If the edit type is delete then delete the course
		if(editType.equalsIgnoreCase("delete"))
		{
			Course courseInfo = courseDao.getCourseInfoWithSection(courseName, section);
			//put error if can not find the course in the database
			if(courseInfo == null)
			{
				putError(object, "The course can not be found in the database.", response);
				return;
			}
			
			int courseId = courseInfo.getCourseId();
			courseDao.deleteCourse(courseName);
			studentCourseDao.deleteAllCourseStudents(courseId);
			instructorCourseDao.deleteAllCourseInstructors(courseId);
			if(userType.equalsIgnoreCase("instructor"))
			{
				//delete the correlation between instructor and course
				instructorCourseDao.deleteCorrelation(userId, courseId);
			}
			putTrue(object, response);
			return;
		}
		else
		{
			//Get the rest of the parameters and check if null or blank
			String location = request.getParameter("location");
			String time = request.getParameter("time");
			String days = request.getParameter("days");
			//int courseId = Integer.parseInt(request.getParameter("courseId"));
			//String id = request.getParameter("courseId");
			
			if(isNull(object, location, "location", response)) return;
			if(isBlank(object, location, "location", response)) return;
			if(isNull(object, time, "time", response)) return;
			if(isBlank(object, time, "time", response)) return;
			if(isNull(object, days, "days", response)) return;
			if(isBlank(object, days, "days", response)) return;
			//if(isNull(object, id, "course id", response)) return;
			//if(isBlank(object, id, "coures id", response)) return;
			
			//int courseId = Integer.parseInt(id);
			
			//Check if the course name is already in the current list
			List<Course> currentList = courseDao.getAvailableCourseList();
			
			//boolean key used to return the error message back to the client
			boolean found = false;
			//If the list is not empty or null proceed to see if the course is already in the database.
			if(!(currentList == null || currentList.isEmpty()))
			{
				//Loop to see if the course is in current list
				for(int i = 0; i < currentList.size(); i++)
				{
					Course currentCourse = currentList.get(i);
					if(currentCourse.getName().equalsIgnoreCase(courseName) && currentCourse.getSection().equalsIgnoreCase(section))
					{
						found = true;
						putError(object, "The course is already in the data base. Please add a different course or delete the course you want to modify and add it again.", response);
						break;
					}
				}
			}
		
			if(found) return;
			//Add the course to the database
			courseDao.createCourse(courseName, location, time, days, section);
			
			if(userType.equalsIgnoreCase("instructor"))
			{
				//create the correlation between instructor and course
				Course courseInfo = courseDao.getCourseInfoWithSection(courseName, section);
				//put error if can not find the course in the database
				if(courseInfo == null)
				{
					putError(object, "The course can not be found in the database.", response);
					return;
				}
				
				int courseId = courseInfo.getCourseId();
				instructorCourseDao.createCorrelation(userId, courseId);
			}
			putTrue(object, response);
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
