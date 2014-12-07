package edu.iastate.cysquare;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorProfile extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private Button home, add, delete;
	private final static String classListURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private final static String modifyURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/modifyCourse";
	private String className, section;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructor_profile);
		
		home = (Button)findViewById(R.id.home_button);
		add = (Button)findViewById(R.id.add_button);
		delete = (Button)findViewById(R.id.delete_button);
		
		populateInstructorClassListView();
		registerClick();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent homeIntent = new Intent(v.getContext(), InstructorWelcome.class);
				startActivity(homeIntent);
			} //end onClick(View v)
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(v.getContext(), CreateClass.class);
				startActivity(addIntent);
			} //end onClick(View v)
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
				JSONObject deleteClass = new JSONObject();
				deleteClass.put("name", className);
				deleteClass.put("section", section);
				deleteClass.put("editType", "delete");
				deleteClass.put("username", getUsername());
				
				new PostWithAsync(modifyURL, deleteClass).execute();
				} catch (JSONException e) {
				}
				
				Intent classesIntent = new Intent(v.getContext(), InstructorProfile.class);
				startActivity(classesIntent);
			}
		});
		
	} //end onCreate(Bundle savedInstanceState)

	private void populateInstructorClassListView() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", getUsername());
			jo.put("editType", "instructor");
			new PostWithAsync(classListURL, jo).execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
    private String getUsername() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("username", "false");
	}
    
    private void createClassListArray(JSONObject response) {
    	try {
				int size = response.getInt("size");
				String[] classes = new String[size];
				for (int i=0; i<size; i++) {
					String courseKey = "Course";
					int courseKeyNumber = i+1;
					courseKey = courseKey.concat(Integer.toString(courseKeyNumber));
					classes[i] = response.getString(courseKey);
				}
				
	    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, classes);
	    	ListView classList = (ListView)findViewById(R.id.instructorClassesListView);
	    	classList.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
	private void registerClick() {
		ListView classList = (ListView)findViewById(R.id.instructorClassesListView);
		classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				getClassNameAndSection(tv.getText().toString());
				Toast.makeText(InstructorProfile.this, className + " Section " + section + " is selected", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void getClassNameAndSection(String courseInfo) {
		Scanner scan = new Scanner(courseInfo);
		while (scan.hasNext()) {
			String scanned = scan.next();
			if (scanned.equalsIgnoreCase("Name:")) {
				className = scan.next();
			}
			else if (scanned.equalsIgnoreCase("Section:")) {
				section = scan.next();
			}
		}
		scan.close();
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
				if(responseObject.has("status") && responseObject.getString("status").equals("error")) {
					Toast.makeText(InstructorProfile.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else {
					if (URL.equals(classListURL)) createClassListArray(responseObject);
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
}
