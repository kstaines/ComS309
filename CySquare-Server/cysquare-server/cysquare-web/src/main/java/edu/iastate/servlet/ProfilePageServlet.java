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
import edu.iastate.domain.StudentCourses;
import edu.iastate.domain.UserAccount;

@WebServlet("/profilePage")
public class ProfilePageServlet extends HttpServlet{

	private static final long serialVersionUID = -4151895874414506005L;
	
	private AccountDAO accountDao = new AccountDAO();
	private StudentCourseDAO studentCourse = new StudentCourseDAO();
	private CourseDAO courseInfo = new CourseDAO();
	private Course course = new Course();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username_string = request.getParameter("username");
	
		
		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		JSONObject profile = new JSONObject();
		
		//check if username_string is null or blank
		if(username_string == null)
		{
			putError(profile, "The username is null", response);
			return;
		}
		if(username_string.equalsIgnoreCase(""))
		{
			putError(profile, "The username is blank", response);
			return;
			
		}
		
		UserAccount user = accountDao.getAccountInfo(username_string);
		try 
		{
			int points = user.getTotalPts();
			profile.put("points", points);
			
			//course id and user id			
			//get courses from the student course dao
			//a list would be the course id
			List<StudentCourses> courseList = studentCourse.getCourses(user.getUserId());
			
			//if course list is null or empty send message saying no courses
			if(courseList == null || courseList.isEmpty())
			{
				putError(profile, "You do not have any courses added to your profile, please add courses", response);
				return;
			}
			//put how many courses the user has 
			profile.put("size", courseList.size());
			
			//get the course table with that course id
			for(int i = 0; i < courseList.size(); i++)
			{
				course = courseInfo.getCourseInfoById(courseList.get(i).getCourseId());
				
				profile.put("course" + (i+1), "Course name: " + course.getName() + " Points: " + courseList.get(i).getPoints());
				
			}
			profile.write(response.getWriter());
			
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
