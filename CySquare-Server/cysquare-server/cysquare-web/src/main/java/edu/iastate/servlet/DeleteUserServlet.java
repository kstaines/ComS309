package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;

public class DeleteUserServlet extends HttpServlet{

	private static final long serialVersionUID = 7102890407783913618L;
	
private AccountDAO  account_dao = new AccountDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get the json string from the client side
		String json_string = request.getParameter("Json");
		String username = "";
		
		//Set the response type
		response.setContentType("application/json");
		
		//check if username_string is null or blank
		if((json_string == null) || (json_string.equalsIgnoreCase("")))
		{
			JSONObject error_response = new JSONObject();
			try {
				error_response.put("status", false);
				error_response.write(response.getWriter());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Get the key
		JSONObject json_request = new JSONObject();
		try {
			json_request.getJSONObject(json_string);
			 username = json_request.getString("username");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//Send the username to the DAO to erase the user account
		account_dao.deleteUserAccount(username);
		
		
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
