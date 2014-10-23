package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;
import edu.iastate.domain.UserAccount;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6761544027811752080L;
	
	private AccountDAO accountDao = new AccountDAO();

	/**
	 * Returns a HTTP response back to the client as a JSON object.
	 * This method receives the request from the client and processes
	 * the login information with the information received by the DAO (Database Access Object).
	 * 
	 * @param request An HTTP request received by the client to be processed
	 * @param response An HTTP response to send to the client as a JSON object with the status of the process			
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username_string = request.getParameter("username");
		String password_string = request.getParameter("password");
		
		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//check if username_string is null or blank
		if(username_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is null");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(username_string.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is blank");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		//check if password_string is null or blank
		if(password_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The password is null");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(password_string.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The password is blank");
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
				error_response.put("status", "There is no user by that name in the database.");
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
					error_response.put("status", "The passwords do not match.");
					error_response.write(response.getWriter());
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}		
		}
		//if the user type is not student then it needs approval. This applies to instructor and administration.
		if(!newuser.getApproved().equalsIgnoreCase("approved"))
		{
			JSONObject error_response = new JSONObject();
			try{
				error_response.put("status", "Your account needs to be approved.");
				error_response.write(response.getWriter());
			} catch(JSONException e) {
				e.printStackTrace();
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
