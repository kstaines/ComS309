package edu.iastate.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import edu.iastate.dao.impl.AccountDAO;
//import edu.iastate.domain.UserAccount;

public class CreateUserServletTest {
	@Mock
	private AccountDAO accountDao;
	
	@InjectMocks
	private CreateUserServlet createUserServlet = new CreateUserServlet();
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void testParseJson() throws IOException, JSONException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		JSONObject obj = new JSONObject();
		obj.put("username", "user");
		obj.put("password", "pass");
		
		
		Mockito.when(request.getParameter("username")).thenReturn((String) obj.get("username"));
		Mockito.when(request.getParameter("password")).thenReturn((String) obj.get("password"));
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		//Mockito.doThrow(new IOException()).when(accountDao).createUserAccount(obj.get("username").toString(), obj.get("password").toString());
		
		createUserServlet.doPost(request, response);
		Mockito.verify(accountDao).createUserAccount(obj.getString("username").toString(), obj.getString("password").toString());
		System.out.println(stringWriter.toString());
	}

}
