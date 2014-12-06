package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;

@WebServlet("/manageUser")
public class ManageUserPageServlet extends HttpServlet {

	private static final long serialVersionUID = 5662658407585399235L;
	private AccountDAO accountDao = new AccountDAO ();
	

	/**
	 * Manages the users by either deleting them from the database or approving their accounts.
	 * @param request An HTTP request received by the client to be processed
	 * @param response An HTTP response to send to the client as a JSON object with the status of the process.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//Get the request parameters
		String user = request.getParameter("username");
		String editType = request.getParameter("editType");
		
		//Set the response type
		response.setContentType("application/json");
		JSONObject object = new JSONObject ();
		
		//check if username or editType is null or blank
		if(hasError(object, user, "username", response)) return;
		if(hasError(object, editType, "edit type", response)) return;
		
		if(!(editType.equalsIgnoreCase("delete") || editType.equalsIgnoreCase("approve")))
		{
			putError(object, "The edit type should be delete or approve", response);
			return;
		}
		else if(editType.equalsIgnoreCase("delete"))
		{
			accountDao.deleteUserAccount(user);
			putTrue(object, response);
			return;
		}
		else
		{
			accountDao.approveUserAccount(user);
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
