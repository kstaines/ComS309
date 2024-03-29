package edu.iastate.cysquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	private EditText username=null;
	private EditText password=null;
	private Button login;
	private HttpClient http;
	private HttpPost request;
	private HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.editText_username);
        password = (EditText)findViewById(R.id.editText_password);
        login = (Button)findViewById(R.id.button_login);
        
        http = new DefaultHttpClient();
        request = new HttpPost("proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/login");
        
    }
    
    public void login(View view){
//    	if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
//    		Toast.makeText(getApplicationContext(), "Logging in...",
//    		Toast.LENGTH_LONG).show();
//    	}
//    	else{
//    		Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
//    	}
    	
    	JSONObject jo = new JSONObject();
    	
    	try
    	{
			jo.put("username", username.getText().toString());
			jo.put("password", password.getText().toString());

			StringEntity mySE = new StringEntity(jo.toString());
			mySE.setContentType("application/json;charset=UTF-8");
			request.setEntity(mySE);
			
			response = http.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder build = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				build.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(build.toString());
			JSONObject responseObject = new JSONObject(tokener);
			
	    	if(responseObject.get("status") == "true"){
	    		Toast.makeText(getApplicationContext(), "Login Successful",
	    		Toast.LENGTH_LONG).show();
	    	}
	    	else{
	    		Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
	    	}
    	}
    	catch (JSONException e)
    	{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
