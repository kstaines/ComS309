package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

	private Spinner spinner;
	private Button back, create;
	private EditText username, password, confirmPassword;
	private String userType;
	private Intent studentWelcomeIntent, unapprovedInstructorWelcomeIntent, mainIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
		
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
				mainIntent = new Intent(v.getContext(), MainActivity.class);
				startActivity(mainIntent);
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
//			String url = "http://10.26.42.223:8081/createUser";

			//Create message
	    	JSONObject jo = new JSONObject();	
	    	try{
	    		jo.put("username", username.getText().toString());
				jo.put("password", password.getText().toString());
				jo.put("usertype", userType);
				
				//Send message and get response
				JSONCommunication jc = new JSONCommunication();
				String build = jc.sendPost(url, jo);
				
				return build;
	    	}
	    	catch (JSONException e){
				e.printStackTrace();
			}
	    	catch (UnsupportedEncodingException e) {
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
		    	else {
		    		// Value in the response json object specifies the error
		    		Toast.makeText(getApplicationContext(), responseObject.getString("status"), Toast.LENGTH_LONG).show();
		    	}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
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
