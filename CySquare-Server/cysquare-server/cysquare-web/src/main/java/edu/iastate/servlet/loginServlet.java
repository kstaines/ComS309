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

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		
		response.setContentType("text/json");	//Check syntax on content types
		JSONObject object = new JSONObject();
		try {
			object.write(response.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
