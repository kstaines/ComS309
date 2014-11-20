package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StudentClasses extends Activity implements OnItemSelectedListener{
	public static final String PREFS_NAME = "MyPreferencesFile";
	
	private Button home, add, delete;
	private Intent homeIntent, classesIntent;
	private Spinner spinner;
	private final static String classListURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private final static String addDeleteClassURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classPage";
	private final static String studentClassListURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classStudent";
	private String className, section;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_classes); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		
		createClassListSpinner();
		spinner.setOnItemSelectedListener(this);

		add = (Button)findViewById(R.id.add_button);
		delete = (Button)findViewById(R.id.delete_button);
		
		displayStudentClassListView();
		registerClick();
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
				JSONObject addClass = new JSONObject();
				addClass.put("name", className);
				addClass.put("section", section);
				addClass.put("editType", "add");
				String user = getUsername();
				addClass.put("username", user);
				
				new PostWithAsync(addDeleteClassURL, addClass).execute();
				
				} catch (JSONException e) {
				}
				
				classesIntent = new Intent(v.getContext(), StudentClasses.class);
				startActivity(classesIntent);
			} //end onClick(view v)
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
				
				new PostWithAsync(addDeleteClassURL, deleteClass).execute();
				} catch (JSONException e) {
				}
				
				classesIntent = new Intent(v.getContext(), StudentClasses.class);
				startActivity(classesIntent);
			}
		});
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), StudentWelcome.class);
				goHome();
			} //end onClick(view v)
		});
	} //end onCreate(Bundle savedInstanceState)
	
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
    
    private void goHome(){
    	startActivity(homeIntent);
    }
    
    private void createClassListSpinner() {
    	spinner = (Spinner) findViewById(R.id.classList_spinner);
    	JSONObject jo = new JSONObject();
    	new PostWithAsync(classListURL, jo).execute();
    }
    
    private void createSpinnerArray(JSONObject response) {
    	try {
				int size = response.getInt("size");
				String[] spinnerArray = new String[size];
				for (int i=0; i<size; i++) {
					String courseKey = "Course";
					int courseKeyNumber = i+1;
					courseKey = courseKey.concat(Integer.toString(courseKeyNumber));
					spinnerArray[i] = response.getString(courseKey);
				}
				finishSpinner(spinnerArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    private void finishSpinner(String[] array) {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    }
  
    
    //gets username from preferences file, if username does not exist, returns "false"
    private String getUsername() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("username", "false");
	}

    @Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String courseInfo = (String) spinner.getSelectedItem();
		getClassNameAndSection(courseInfo);
	}
    
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//Do Nothing
	}
    
    private void displayStudentClassListView() {
    	JSONObject jo = new JSONObject();
    	try {
			jo.put("username", getUsername());
			new PostWithAsync(studentClassListURL, jo).execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    private void createClassListArray(JSONObject response) {
    	try {
				int size = response.getInt("size");
				String[] studentClasses = new String[size];
				for (int i=0; i<size; i++) {
					String courseKey = "Course";
					int courseKeyNumber = i+1;
					courseKey = courseKey.concat(Integer.toString(courseKeyNumber));
					studentClasses[i] = response.getString(courseKey);
				}
				
	    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, studentClasses);
	    	ListView classList = (ListView)findViewById(R.id.studentClassesListView);
	    	classList.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
    
	private void registerClick() {
		ListView studentClassList = (ListView)findViewById(R.id.studentClassesListView);
		studentClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				getClassNameAndSection(tv.getText().toString());
				Toast.makeText(StudentClasses.this, className + " is selected", Toast.LENGTH_LONG).show();
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
		private String url;
		private JSONObject jo;
		public PostWithAsync(String url, JSONObject jo) {
			this.url = url;
			this.jo = jo;
		}
		
		@Override
		protected String doInBackground(String... arg0) {
	    	try{
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
				if (responseObject.has("status") && responseObject.getString("status").equals("error")) {
					Toast.makeText(StudentClasses.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else {
					if(url.equals(classListURL)) createSpinnerArray(responseObject);
					else if (url.equals(addDeleteClassURL)) Toast.makeText(getApplicationContext(), "Add/Delete was successful", Toast.LENGTH_LONG).show();
					else if (url.equals(studentClassListURL)) createClassListArray(responseObject);
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
	}
}