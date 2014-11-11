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
public class ClassList extends HttpServlet{

	private static final long serialVersionUID = 8438444817401559341L;
	
	private CourseDAO courseDao = new CourseDAO ();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		List<Course> courseList = courseDao.getAvailableCourseList();
		JSONObject list = new JSONObject ();
		try
		{
			for(int i = 0; i < courseList.size(); i++)
			{
				Course course = courseList.get(i);
				list.put("Course" +(i+1), "Course name: " + course.getName() + " Section: " + course.getSection() +
							 " Location: " + course.getLocation() + " Days: " + course.getDays() + " Time: " + course.getTime());
			}
			list.write(response.getWriter());
		}
		
		catch (JSONException e)
		{
			
			e.printStackTrace();
		}
	}

}