package edu.iastate.servlet;

import java.io.IOException;

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
//import edu.iastate.domain.StudentCourses;
import edu.iastate.domain.UserAccount;
@WebServlet("/classPage")
public class ClassPageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5013146992002421443L;
	
	private AccountDAO accountDao = new AccountDAO();
	private StudentCourseDAO studentCourseDao = new StudentCourseDAO();
	private CourseDAO courseDao = new CourseDAO();
	private Course course = new Course();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username_string = request.getParameter("username");
		String edit_type = request.getParameter("editType");
		String name = request.getParameter("name");
		String section = request.getParameter("section");
	
		
		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//the json object is classes
		JSONObject classes = new JSONObject();
		
		//get user info to make or delete correlations 
		UserAccount user = accountDao.getAccountInfo(username_string);
		
		//check if username_string is null or blank
		checkNull(classes, username_string, "username",response);
		checkBlank(classes, username_string, "username", response);
		
		//check if the edit type is null or blank
		checkNull(classes, edit_type, "edit type", response);
		checkBlank(classes, edit_type, "edit type", response);
		
		//the parameter will always have the class name so must check if null or empty
		checkNull(classes, name, "class name", response);
		checkBlank(classes, name, "class name", response);
		
		//the parameter will always have the course id so must check if null or empty
		checkNull(classes, section, "section", response);
		checkBlank(classes, section, "section", response);
		//get course id from the database based on the course name and section		
		course = courseDao.getCourseInfoWithSection(name, section);
		Integer courseId = course.getCourseId();
		
		//if the edit type is to delete then delete from the database
		if(edit_type.equalsIgnoreCase("delete"))
		{
			
			studentCourseDao.deleteCorrelation(user.getUserId(), courseId);
			putTrue(classes, response);
		}
		
		//if the edit type is to add a class to their profile page
		if(edit_type.equalsIgnoreCase("add"))
		{
			studentCourseDao.createCorrelation(user.getUserId(), courseId);
			putTrue(classes, response);
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
	private void checkNull(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck == null)
		{
			putError(object, "The " + toType + " is null.", response);
		}
	}
	
	private void checkBlank(JSONObject object, String toCheck, String toType, HttpServletResponse response)
	{
		if(toCheck.equalsIgnoreCase(""))
		{
			putError(object, "The " + toType + " is blank.", response);
		}
	}

}
