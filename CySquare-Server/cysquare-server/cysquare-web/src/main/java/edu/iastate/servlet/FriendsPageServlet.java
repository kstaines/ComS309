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
		
		JSONObject friend = new JSONObject ();
		
		checkNull(friend, username, "user", response);
		checkBlank(friend, username, "user", response);
		
		UserAccount userAccount = accountDao.getAccountInfo(username);
		List<Friend> friendList = friendDao.getFriendList(userAccount.getUserId());
		
		//check if the user has any friends
		if(friendList.size() == 0 || friendList == null)
		{
			putError(friend, "Currently has no friends.", response);
		}
		//counter values 
		int approveCounter = 0;
		int notApproveCounter = 0;
		//Loop through the friend list to return the friends of this user
		for (int i = 0; i < friendList.size(); i++)
		{
			if(friendList.get(i).getApprovalStatus().equalsIgnoreCase("y"))
			{
				try
				{
					UserAccount friendAccount = accountDao.getAccountInfoById(friendList.get(i).getFriendId());
					friend.put("friendapproved" + (approveCounter+1), friendAccount.getUsername());
					approveCounter = approveCounter + 1;
				} 
				catch (JSONException e) {
					
					e.printStackTrace();
				}
			
				
			}
			else
			{
				//put a friends awaiting 
				UserAccount friendAccount = accountDao.getAccountInfoById(friendList.get(i).getFriendId());
				try {
					friend.put("friendnotapproved" + (notApproveCounter+1), friendAccount.getUsername());
					notApproveCounter = notApproveCounter + 1;
				} catch (JSONException e) {
	
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

