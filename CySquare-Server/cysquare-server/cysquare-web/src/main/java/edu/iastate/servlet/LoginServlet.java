package edu.iastate.servlet;

import java.io.IOException;

import edu.iastate.domain.UserAccount;
import edu.iastate.dao.impl.AccountDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6761544027811752080L;
	
	private AccountDAO accountDao = new AccountDAO();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username_string = "";
		String password_string = "";
		//getParameter method returns a string of the JSON Object from the android project
		String json_string = request.getParameter("Json");

		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//check if username_string is null or blank
		if((json_string == null) || (json_string.equalsIgnoreCase("")))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", false);
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//Get the key
		try {
			JSONObject json_request = new JSONObject(json_string);
			username_string = json_request.getString("username");
			password_string = json_request.getString("password");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		//check if password_string is null or blank
		if((password_string == null) || (password_string.equalsIgnoreCase( "")))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", false);
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		
		//send the username to the dao
		//receive a pojo from the dao
		UserAccount newuser = accountDao.getAccountInfo(username_string);
		String pojo_password = newuser.getPassword();
		//if there is no username the dao will return a null
		//then should send a json object with text stating there is no user by that name
		if(newuser.getUsername() == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", false);
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		//else compare passwords
		else
		{
			//if  they are not the same
			if(!password_string.equals(pojo_password))
			{
				JSONObject error_response = new JSONObject();
				try {
					error_response.put("status", false);
					error_response.write(response.getWriter());
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}		
		}
		JSONObject object = new JSONObject();
		try 
		{
			object.put("status", true);
			object.write(response.getWriter());
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
