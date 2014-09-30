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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//getParameter method returns a string of the JSON Object from the android project
		String username_string = request.getParameter("username");
		String password_string = request.getParameter("password");

		//Set the content type to JSON for when this sends the response
		response.setContentType("application/json");
		
		//check if username_string is null or blank
		if((username_string == null) || (username_string == ""))
		{
			response.sendError(401, "The username is invalid");
		}
		//check if password_string is null or blank
		if((password_string == null) || (password_string == ""))
		{
			response.sendError(401, "The password is invalid");
		}

		
		//send the username to the dao
		//receive a pojo from the dao
		
		//if there is no username the dao will return a null
		//then should send a json object with text stating there is no user by that name
		/*if(pojo_username == null)
		{
			response.sendError(401, "There is no user by that name");
		}*/
		
		//else compare passwords
		/*else
		{
			//if the passwords have different lengths then they are not the same
			if(password_string.length() != pojo_password.length())
			{
				response.sendError(401, "The password does not match the username");
			}
			for(int i = 0; i < password_string.length(); i++)
			{
				char c_one = password_string.charAt(i);
				char c_two = pojo_password.charAt(i);
				if(c_one != c_two)
				{
					response.sendError(401, "The password does not match the username");
				}
			}
			 
			
			
		}*/
		
		
		
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