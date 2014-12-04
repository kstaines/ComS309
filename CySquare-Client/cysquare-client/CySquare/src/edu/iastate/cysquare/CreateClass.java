package edu.iastate.cysquare;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateClass extends ActionBarActivity {
	public static final String PREFS_NAME = "MyPreferencesFile";
	private String modifyURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/modifyCourse";
	private Button back, add;
	private TimePicker timePicker;
	private String className, section, location, days, time;
	private EditText entered_className, entered_section, entered_location, entered_days;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_class);
		
		back = (Button)findViewById(R.id.button_back);
		add = (Button)findViewById(R.id.add_button);
		entered_className = (EditText)findViewById(R.id.entered_classname);
		entered_section = (EditText)findViewById(R.id.entered_section);
		entered_location = (EditText)findViewById(R.id.entered_location);
		entered_days = (EditText)findViewById(R.id.entered_days);
		
		timePicker = (TimePicker)findViewById(R.id.timePicker1);
		timePicker.setIs24HourView(true);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent backIntent;
				String usertype = getUserType();
				if (usertype.equalsIgnoreCase("admin")) {
					backIntent = new Intent(v.getContext(), AdminClasses.class);
					startActivity(backIntent);
				}
				else if (usertype.equalsIgnoreCase("instructor")) {
					backIntent = new Intent(v.getContext(), InstructorProfile.class);
					startActivity(backIntent);
				}
				
			} //end onClick(View v)
		}); //end back.setOnClickListener
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (saveTextFields() == true) {
					Toast.makeText(getApplicationContext(), "One or more class information fields contain spaces. Please enter the information without spaces and try again.", Toast.LENGTH_LONG).show();
				}
				else {
					JSONObject jo = saveFieldsInJSONObject();
					new PostWithAsync(modifyURL, jo).execute();
				}
				
			} //end onClick(View v)
		}); //end back.setOnClickListener
		
	}
	
    private String getUserType() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("usertype", "false");
	}
    
    private Boolean saveTextFields() {
    	
    	className = entered_className.getText().toString();
    	section = entered_section.getText().toString();
    	location = entered_location.getText().toString();
    	days = entered_days.getText().toString();
    	time = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00";
    	
    	return checkForSpaces(className, days);
    }
    
    private Boolean checkForSpaces(String classname, String days) {
    	if (classname.contains(" ") || days.contains(" ")) return true;
    	return false;
    }

    private JSONObject saveFieldsInJSONObject() {
    	JSONObject jobj = new JSONObject();
    	try {
			jobj.put("coursename", className);
			jobj.put("editType", "add");
			jobj.put("location", location);
			jobj.put("time", time);
			jobj.put("section", section);
			jobj.put("days", days);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    	return jobj;
    }
    
    private class PostWithAsync extends AsyncTask<String, String, String> {
    	private String URL;
    	private JSONObject sendJSONObject;
		public PostWithAsync(String URL, JSONObject jo) {
			this.URL = URL;
			sendJSONObject = jo;
		}

		@Override
		protected String doInBackground(String... arg0) {
		
			try{
				JSONCommunication jc = new JSONCommunication();
				String build = jc.sendPost(URL, sendJSONObject);
				return build;
	    	}
	    	catch (JSONException e){
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
				if(responseObject.getString("status").equals("true")) {
					Toast.makeText(CreateClass.this, "The new class has been added!", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(CreateClass.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
}


