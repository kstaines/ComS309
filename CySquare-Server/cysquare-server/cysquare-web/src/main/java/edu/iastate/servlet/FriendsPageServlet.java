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
import edu.iastate.dao.impl.FriendDAO;
import edu.iastate.domain.Friend;
import edu.iastate.domain.UserAccount;


@WebServlet("/friendsPage")
public class FriendsPageServlet extends HttpServlet{

	private static final long serialVersionUID = 3563797253016667883L;
	private AccountDAO accountDao = new AccountDAO ();
	private FriendDAO friendDao = new FriendDAO ();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username = request.getParameter("user");
		String friendName = request.getParameter("friend");
		
		JSONObject friend = new JSONObject ();
		
		checkNull(friend, username, "user", response);
		checkBlank(friend, username, "user", response);
		
		checkNull(friend, friendName, "friends name", response);
		checkBlank(friend, friendName, "friends name", response);
		
		UserAccount userAccount = accountDao.getAccountInfo(username);
		List<Friend> friendList = friendDao.getFriendList(userAccount.getUserId());
		
		//Loop through the friend list to return the friends of this user
		for (int i = 0; i < friendList.size(); i++)
		{
			if(friendList.get(i).getApprovalStatus().equalsIgnoreCase("y"))
			{
				try
				{
					friend.put("friend" + (i+1), friendList.get(i).getFriendId());
				} 
				catch (JSONException e) {
					
					e.printStackTrace();
				}
			
				
			}
			
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

