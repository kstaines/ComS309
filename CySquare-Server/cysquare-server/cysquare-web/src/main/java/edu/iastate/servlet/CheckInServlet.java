package edu.iastate.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.dao.impl.AccountDAO;

public class CheckInServlet extends HttpServlet
{
	private static final long serialVersionUID = 943348396279963269L;
	
	private AccountDAO account_dao = new AccountDAO();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get the string from the client side
		String username_string = request.getParameter("username");
		//Assuming right now the location will be sent as a string
		String location = request.getParameter("location");
		
		//Set the response type
		response.setContentType("application/json");
		
		//check if username_string and location is null or blank
		if((username_string == null) || (location == null) || (username_string.equalsIgnoreCase("")) || (location.equalsIgnoreCase("")))
		{
			JSONObject error_response = new JSONObject();
			try 
			{
				error_response.put("status", false);
				error_response.write(response.getWriter());
			} 
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		//Get the class list which has the location, which will identify if the user is checking into class, or studying
		//get pojo_class_info from the DAO 
		
		//check in each pojo_class_info to find the location
		//if the pojo_location is the same as the current location
		//then check into class
		//else check into studying
		//return if checked into class or studying
		
		
		
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
