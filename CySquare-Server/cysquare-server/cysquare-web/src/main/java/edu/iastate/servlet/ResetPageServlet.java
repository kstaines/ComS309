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
import edu.iastate.dao.impl.StudentCourseDAO;
import edu.iastate.domain.UserAccount;

@WebServlet("/resetPage")

public class ResetPageServlet extends HttpServlet{

	
	private static final long serialVersionUID = -2748029580158351787L;
	private AccountDAO accountDao = new AccountDAO ();
	private StudentCourseDAO studentCourseDao = new StudentCourseDAO ();

	/**
	 * Processes and resets all the student user points and class lists.
	 * @param request An HTTP request received by the client to be processed 
	 * @param response An HTTP response to send to the client as a JSON object with the status of the process
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//No request parameters from the client
		//Set the response type
		response.setContentType("application/json");
		JSONObject object = new JSONObject ();
		//Get all the users
		List<UserAccount> allUsers = accountDao.getAllUsers();
		
		//check if there are no users to reset
		
		if(allUsers == null || allUsers.isEmpty())
		{
			putError(object, "There are no users to be reset.", response);
			return;
		}
		
		//Delete the all the student user points and class list
		accountDao.resetStudentPoints();
		studentCourseDao.deleteAllStudentCourseCorrelations();
		
		putTrue(object, response);
		return;
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
	
}
