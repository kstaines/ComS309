package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminClasses extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	
	private Button home, add, delete;
	private Intent homeIntent, addIntent;
	private final static String classListURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private final static String modifyURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/modifyCourse";
	private String className, section;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_classes); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		add = (Button)findViewById(R.id.add_button);
		delete = (Button)findViewById(R.id.delete_button);
		
		displayAllClasses();
		registerClick();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), AdminWelcome.class);
				goHome();
			} //end onClick(view v)
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addIntent = new Intent(v.getContext(), CreateClass.class);
				startActivity(addIntent);
			} //end onClick(view v)
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JSONObject jo = new JSONObject();
				try {
					jo.put("coursename", className);
					jo.put("section", section);
					jo.put("editType", "delete");
					new PostWithAsync(modifyURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Intent refreshAfterDelete = new Intent(v.getContext(), AdminClasses.class);
				startActivity(refreshAfterDelete);
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
    
    private void displayAllClasses() {
    	JSONObject jo = new JSONObject();
    	new PostWithAsync(classListURL, jo).execute();
    }
    
    private void createClassesArrayAndListView(JSONObject response) {
    	try {
			int size = response.getInt("size");
			String[] classesArray = new String[size];
			for (int i=0; i<size; i++) {
				String courseKey = "Course";
				courseKey = courseKey.concat(Integer.toString(i+1));
				classesArray[i] = response.getString(courseKey);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, classesArray);
			ListView classList = (ListView)findViewById(R.id.allClassesListView);
			classList.setAdapter(adapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
	private void registerClick() {
		ListView classList = (ListView)findViewById(R.id.allClassesListView);
		classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				getClassNameAndSection(tv.getText().toString());
				Toast.makeText(AdminClasses.this, className + " Section " + section + " is selected", Toast.LENGTH_LONG).show();
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
					Toast.makeText(AdminClasses.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else {
					if(url.equals(classListURL)) createClassesArrayAndListView(responseObject);
					else if (url.equals(modifyURL)) {
						if (responseObject.getString("status").equals("true")) {
							Toast.makeText(AdminClasses.this, "This class was deleted", Toast.LENGTH_LONG).show();
						}
					}
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
}
