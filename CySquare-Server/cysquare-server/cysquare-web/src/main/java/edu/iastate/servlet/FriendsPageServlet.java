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

	
	private static final long serialVersionUID = -9026444719267002619L;
	private FriendDAO friendDao = new FriendDAO ();
	private AccountDAO accountDao = new AccountDAO ();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		//Get the parameters
		String username = request.getParameter("username");
		String friend = request.getParameter("friendname");
		String editType = request.getParameter("editType");
		
		//Set the response type
		response.setContentType("application/json");
		
		JSONObject object = new JSONObject ();
		
		
		//Check if the parameter are null or blank
		if(isNull(object, username, "username", response)) return;
		if(isBlank(object, username, "username", response)) return;
		
		if(isNull(object, friend, "friend", response)) return;
		if(isBlank(object, friend, "friend", response)) return;
		
		if(isNull(object, editType, "edit type", response)) return;
		if(isBlank(object, editType, "edit type", response)) return;
		
		//Get the user account info from the user and the friend
		UserAccount useraccount = accountDao.getAccountInfo(username);
		UserAccount friendaccount = accountDao.getAccountInfo(friend);
		
		//Get the user id and friend id
		Integer userId = useraccount.getUserId();
		Integer friendId = friendaccount.getUserId();
		
		if(editType.equalsIgnoreCase("delete"))
		{
			friendDao.deleteFriendship(userId, friendId);
			putTrue(object, response);
			return;
		}
		else if(editType.equalsIgnoreCase("add"))
		{
			//If the friend and the user try to add each other then they are automatically approved
			List<Friend> friendList = friendDao.getFriendList(userId);
			
			boolean approved = false;
			
			//if the friend list is not empty then check if they both wanted to add each other
			if(!friendList.isEmpty())
			{
				for(int i = 0; i < friendList.size(); i++)
				{
					if(friendList.get(i).getFriendId().equals(friendId))
					{
						friendDao.approveFriendship(userId, friendId);
						approved = true;
						break;
					}
					else if(friendList.get(i).getStudentId().equals(friendId))
					{
						friendDao.approveFriendship(friendId, userId);
						approved = true;
						break;
					}
				}
			}
			if(!approved)
			{
				friendDao.createFriendship(userId, friendId);
			}
			
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
