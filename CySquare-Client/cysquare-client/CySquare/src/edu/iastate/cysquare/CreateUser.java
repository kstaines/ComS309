package edu.iastate.cysquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.cysquare.domain.Parameter;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateUser extends ActionBarActivity implements OnItemSelectedListener {
	public static final String PREFS_NAME = "MyPreferencesFile";

	private Spinner spinner;
	private Button back;
	private Button create;
	private Intent newIntent;
	private HttpClient http;
	private HttpPost request;
	private HttpResponse response;
	private EditText username;
	private EditText password;
	private EditText confirmPassword;
	private String userType;
	private Intent studentWelcomeIntent, unapprovedInstructorWelcomeIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
        //next two lines take care of NetworkOnMainThreadException
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		createDropDownMenu();
		
		back = (Button)findViewById(R.id.button_back);
		create = (Button)findViewById(R.id.button_createNewUser);
		username = (EditText)findViewById(R.id.editText_username); 
	    password = (EditText)findViewById(R.id.editText_password);
	    confirmPassword = (EditText)findViewById(R.id.editText_confirmpassword);
	    spinner.setOnItemSelectedListener(this);
	    
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				newIntent = new Intent(v.getContext(), MainActivity.class);
				startActivity(newIntent);
			} //end onClick(View v)
		}); //end logout.setOnClickListener
	
		create.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (checkForMatchingPasswords()) {
					studentWelcomeIntent = new Intent(v.getContext(), StudentWelcome.class);
					unapprovedInstructorWelcomeIntent = new Intent(v.getContext(), UnapprovedInstructorWelcome.class);
					new PostWithAsync().execute();
				}
				
			} //end onClick(View v)
		}); //end logout.setOnClickListener
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_user, menu);
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
	
	private void createDropDownMenu() {
		spinner = (Spinner) findViewById(R.id.usertype_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.user_types_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	private boolean checkForMatchingPasswords(){
		if (password.getText().toString().equals(confirmPassword.getText().toString())) {
			return true;
		}
		else {
			Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	private class PostWithAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			String url = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/createUser";
			
			http = new DefaultHttpClient();
	    	HttpConnectionParams.setConnectionTimeout(http.getParams(), 100000); //Timeout Limit
	    	
	    	SharedPreferences username = getSharedPreferences(PREFS_NAME, 0);
	    	SharedPreferences.Editor editor = username.edit();
	    	editor.putString("username", username.toString());
	    	editor.commit();
	    	
	    	//Create message
	    	JSONObject jo = new JSONObject();	
	    	try{
	    		jo.put("username", username.getText().toString());
				jo.put("password", password.getText().toString());
				jo.put("usertype", userType);
				
				//Send message and get response
				String build = sendPost(url, jo);
				
				return build;
	    	}
	    	catch (JSONException e){
				e.printStackTrace();
			}
	    	catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    	catch (ClientProtocolException e) {
				e.printStackTrace();
			}
	    	catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String build) {
			JSONObject responseObject;
			try {
				responseObject = new JSONObject(build);
			
				if(responseObject.getString("status").equals("true")){ //login info was correct/true
		    		
		    		Toast.makeText(getApplicationContext(), "Create User Successful", Toast.LENGTH_LONG).show();

		    		// STUDENT/INSTRUCTOR WELCOME PAGE
		    		if (userType.equals("student")){
		    			startActivityForResult(studentWelcomeIntent, 0);
		    		}
		    		else if (userType.equals("instructor")) {
		    			startActivityForResult(unapprovedInstructorWelcomeIntent, 0);
		    		}
		    	}
		    	else if (!responseObject.getBoolean("status")) {
		    		// Value in the response json object specifies the error
		    		Toast.makeText(getApplicationContext(), responseObject.getString("status"), Toast.LENGTH_LONG).show();
		    	}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
	
	private String sendPost(String url, JSONObject jo) throws ClientProtocolException, IOException, JSONException {
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		Iterator<String> keys = jo.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			Parameter param = new Parameter();
			param.setName(key);
			param.setValue(jo.getString(key));
			parameters.add(param);
		}
		StringEntity mySE = new UrlEncodedFormEntity(parameters);
		mySE.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;")); //setContentType sets content type of the response being sent to the client
		request = new HttpPost(url);
		request.setEntity(mySE);

		response = http.execute(request);
					
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String build = reader.readLine();
		return build;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		int position = spinner.getSelectedItemPosition();
		switch (position) {
		case 0:
			userType = "student";
			break;
		case 1:
			userType = "instructor";
			break;
		case 2:
			userType = "admin";
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
