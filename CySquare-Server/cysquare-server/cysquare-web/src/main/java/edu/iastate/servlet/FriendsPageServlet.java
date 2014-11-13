package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.dao.impl.FriendDAO;
import edu.iastate.domain.UserAccount;

@WebServlet("/friendsPage")
public class FriendsPageServlet extends HttpServlet{

	
	private static final long serialVersionUID = -9026444719267002619L;
	private FriendDAO friendDao = new FriendDAO ();
	private AccountDAO accountDao = new AccountDAO ();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username = request.getParameter("username");
		String friend = request.getParameter("friendname");
		String editType = request.getParameter("editType");
		
		JSONObject object = new JSONObject ();
		
		if(isNull(object, username, "username", response)) return;
		if(isBlank(object, username, "username", response)) return;
		
		if(isNull(object, friend, "friend", response)) return;
		if(isBlank(object, friend, "friend", response)) return;
		
		if(isNull(object, editType, "edit type", response)) return;
		if(isBlank(object, editType, "edit type", response)) return;
		
		//Get the user account info from the user and the friend
		UserAccount useraccount = accountDao.getAccountInfo(username);
		UserAccount friendaccount = accountDao.getAccountInfo(friend);
		
		if(editType.equalsIgnoreCase("delete"))
		{
			friendDao.deleteFriendship(useraccount.getUserId(), friendaccount.getUserId());
			putTrue(object, response);
			return;
		}
		else if(editType.equalsIgnoreCase("add"))
		{
			friendDao.createFriendship(useraccount.getUserId(), friendaccount.getUserId());
			putTrue(object, response);
			return;
		}
		else
		{
			putError(object, "The edit type should be delete or add", response);
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
