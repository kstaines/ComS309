package edu.iastate.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class loginServlet extends HttpServlet {

	private static final long serialVersionUID = -6761544027811752080L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//getParameter method returns a string of the JSON Object from the android project
		String username_string = request.getParameter("username");
		String password_string = request.getParameter("password");
		
		//check if username_string is null or blank
		
		//check if password_string is null or blank
		
		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//send the username to the dao
		//receive a pojo from the dao
		
		//if there is no username the dao will return a null
		//then should send a json object with text stating there is no user by that name
		//else compare passwords
		
		JSONObject object = new JSONObject();
		try 
		{
			
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
