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
import edu.iastate.domain.UserAccount;

public class DeleteUserServletTest {

	@Mock
	private AccountDAO accountDao;
	
	@InjectMocks
	private DeleteUserServlet deleteUser = new DeleteUserServlet();
	
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
		
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("user");
		userAccount.setPassword("pass");
		
		Mockito.when(request.getParameter("username")).thenReturn((String) obj.get("username"));
		Mockito.when(request.getParameter("password")).thenReturn((String) obj.get("password"));
		Mockito.when(response.getWriter()).thenReturn(printWriter);
			
		deleteUser.doPost(request, response);
		Mockito.verify(accountDao).deleteUserAccount(obj.get("username").toString());
		
		System.out.println(stringWriter.toString());
	}

}
