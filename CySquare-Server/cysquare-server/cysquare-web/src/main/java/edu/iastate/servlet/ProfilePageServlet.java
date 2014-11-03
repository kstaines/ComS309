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
import edu.iastate.domain.StudentCourses;
import edu.iastate.domain.UserAccount;

@WebServlet("/profilePage")
public class ProfilePageServlet extends HttpServlet{

	private static final long serialVersionUID = -4151895874414506005L;
	
	private AccountDAO accountDao = new AccountDAO();
	private StudentCourseDAO course = new StudentCourseDAO();
	private CourseDAO courseInfo = new CourseDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username_string = request.getParameter("username");
	
		
		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//check if username_string is null or blank
		if(username_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is null");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(username_string.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is blank");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		UserAccount user = accountDao.getAccountInfo(username_string);
		JSONObject profile = new JSONObject();
		
		try {
			int points = user.getTotalPts();
			profile.put("points", points);
			
			//course id and user id			
			//get courses from the student course dao
			//a list would be the course id
			List<StudentCourses> courseList = course.getCourses(user.getUserId());
			
			//if course list is null or empty send message saying no courses
			if(courseList == null || courseList.isEmpty())
			{
				profile.put("course", "You do not have any courses added to your profile, please add courses");
				profile.write(response.getWriter());
				return;
			}
			
			//get the course table with that course id
			for(int i = 0; i < courseList.size(); i++)
			{
				profile.put("course" + i, courseInfo.getCourseInfoById(courseList.get(i).getCourseId()));
			}
			profile.write(response.getWriter());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
