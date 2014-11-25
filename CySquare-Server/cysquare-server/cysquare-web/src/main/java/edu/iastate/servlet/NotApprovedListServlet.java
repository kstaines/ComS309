package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;


@WebServlet("/approvalList")
public class NotApprovedListServlet extends HttpServlet {

	private static final long serialVersionUID = 4531330599938624077L;
	private AccountDAO accountDao = new AccountDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//No request parameters for this servlet
		
		//Awaiting a method from the dao to return the list to the client.
		
		JSONObject object = new JSONObject ();
		
		/*if(unapprovedList.isEmpty())
		 * {
		 * 		putError(object, "There are no users who unapproved.", response);
		 * 
		 * }
		 * 
		 * else
		 * {
		 * 		//return the list
		 * 		object.put("size", unapprovedList.size());
		 * 		for(int i = 0; i < unapprovedList.size(); i++)
		 * 		{
		 * 			
		 * 		}
		 * 
		 * }
		 */
		
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

}
