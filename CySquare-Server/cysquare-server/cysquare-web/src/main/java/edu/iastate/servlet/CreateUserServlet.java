package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;

public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 943348396279963269L;
	
	private AccountDAO  account_dao = new AccountDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get the string from the client side
		String username_string = request.getParameter("username");
		String password_string = request.getParameter("password");
		//As a group have decided there will be an account type
		String accountType = request.getParameter("type");
		
		//Set the response type
		response.setContentType("application/json");
		
		//check if username or password is null or blank
		if(username_string == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The username is null.");
				error_response.write(response.getWriter());
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(accountType == null)
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The account type is null.");
				error_response.write(response.getWriter());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(accountType.equalsIgnoreCase(""))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", "The account type is blank.");
				error_response.write(response.getWriter());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Send the username and password to the DAO
		account_dao.createUserAccount(username_string, password_string);
		//By specifications by the group, the new DAO will include type
		//account_dao.createUserAccount(username_string, password_string, accountType);
		
		//Send the response that it was successful
		JSONObject correct_response = new JSONObject();
		try {
			correct_response.put("status", true);
			correct_response.write(response.getWriter());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
