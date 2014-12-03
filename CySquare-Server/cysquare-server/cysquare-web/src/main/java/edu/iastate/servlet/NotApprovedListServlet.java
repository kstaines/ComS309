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
import edu.iastate.domain.UserAccount;


@WebServlet("/notApprovedList")


public class NotApprovedListServlet extends HttpServlet {

	private static final long serialVersionUID = 4531330599938624077L;
	private AccountDAO accountDao = new AccountDAO();
	
	/**
	 * Retrieves all the unapproved users from the database and sends this list back to the client.
	 * @param request An HTTP request received by the client  
	 * @param response An HTTP response to send to the client as a JSON object with the status of the process
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//No request parameters for this servlet
		
		JSONObject object = new JSONObject ();
		List<UserAccount> unapprovedList = accountDao.getUnapprovedUsers();
		
		if(unapprovedList == null || unapprovedList.isEmpty())
		{
			putError(object, "There are no users who are unapproved.", response);
			return;
		}
		else
		{			
	 		try {
	 			//return the size
				object.put("size", unapprovedList.size());
				
				//return the list
				for(int i = 0; i < unapprovedList.size(); i++)
		 		{
		 			UserAccount user = unapprovedList.get(i);
		 			object.put("User " + (i+1), user.getUsername());
		 		}
				putTrue(object, response);
				return;
			} catch (JSONException e) 
	 		{
			
				e.printStackTrace();
			}
	 		
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

}
