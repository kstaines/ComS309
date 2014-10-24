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

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 943348396279963269L;
	
	private AccountDAO  account_dao = new AccountDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get the string from the client side
		String username_string = request.getParameter("username");
		String password_string = request.getParameter("password");
		//As a group have decided there will be an account type
		String accountType = request.getParameter("usertype");
		
		//Set the response type
		response.setContentType("application/json");
		
		//check if username or password is null or blank
		if(username_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is null.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(username_string.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is blank.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(password_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The password is null.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(password_string.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The password is blank.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(accountType == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The user type is null.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(accountType.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The user type is blank.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//check if the username is already in the DAO 
		UserAccount user = account_dao.getAccountInfo(username_string);
		if((user != null) && (user.getUsername() != null) && user.getUsername().equalsIgnoreCase(username_string))
		{
			JSONObject error_response = new JSONObject();
			try{
				error_response.put("status", "This username already exists, please enter a different username.");
				error_response.write(response.getWriter());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		//Send the username and password to the DAO
		//account_dao.createUserAccount(username_string, password_string);
		//By specifications by the group, the new DAO will include type
		account_dao.createUserAccount(username_string, password_string, accountType);
		
		//Send the response that it was successful
		JSONObject correct_response = new JSONObject();
		try {
			if(accountType.equalsIgnoreCase("student"))
			{
				account_dao.approveUserAccount(username_string);
				correct_response.put("status", true);
			}
			else
			{
				correct_response.put("status", "Account successfully created, but awaiting for approval.");
			}
			
			correct_response.write(response.getWriter());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
