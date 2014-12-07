package edu.iastate.cysquare;

import java.io.IOException;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorCurrentCheckIns extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	String usernameFromPref;
	private static final String checkInsURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/checkInList";
	private static final String classListURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private Button home;
	private ListView classList;
	private ListView checkInsList;
	private Intent homeIntent;
	private String selectedClassName;
	private String selectedSection;
	private String clickedClass;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructor_current_checkins);
		
		home = (Button)findViewById(R.id.home_button);
		classList = (ListView)findViewById(R.id.class_list);
		checkInsList = (ListView)findViewById(R.id.check_ins_list);
		
		getUsernameFromPrefs();
		populateClassListView();
		registerClick();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), InstructorWelcome.class);
				goHome();
			} //end onClick(View v)
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
    
    private void populateClassListView(){
    	JSONObject jo = new JSONObject();
    	try{
    		jo.put("username", usernameFromPref);
    		jo.put("editType", "Instructor");
    		new PostWithAsync(classListURL, jo).execute();
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void createClassListArrayFromServerResponse(JSONObject response){
    	try{
    		//get list of all students in a class
    		int size = response.getInt("size");
    		String[] classes = new String[size];
    		for (int i=0; i<size; i++){
    			String className = "Course";
    			className = className.concat(Integer.toString(i+1));
    			classes[i] = response.getString(className);
    		}
    		
    		//build adapter
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, classes);
    		//ListView object
    		ListView classList = (ListView)findViewById(R.id.class_list);
    		classList.setAdapter(adapter);
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void registerClick(){
    	classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> arg0, View v, int position, long id){
    			TextView tv = (TextView) v;
    			clickedClass = tv.getText().toString();
    			
    			String splitClassString[] = clickedClass.split(" ");
    			selectedClassName = splitClassString[0]; //grabs 0th element, class name
    			selectedSection = splitClassString[1]; //grabs 1st element, class section
    			Toast.makeText(InstructorCurrentCheckIns.this, "Class selected: " + selectedClassName + " Section: " + selectedSection, Toast.LENGTH_LONG).show();
    			
    			JSONObject jo = new JSONObject();
    			try{
    				jo.put("classname", selectedClassName);
    				jo.put("section", selectedSection);
    				new PostWithAsync(checkInsURL, jo).execute();
    			}
    			catch(JSONException e){
					e.printStackTrace();
				}
    		}
		});
    }
    
    private void createCheckInsListArrayFromServerResponse(JSONObject response){
    	try{
    		int size = response.getInt("size");
    		String[] checkIns = new String[size]; //stores checkins list
    		for(int i=0; i<size; i++){
    			String studentID = "Student";
    			studentID = studentID.concat(Integer.toString(i+1)); //creates Studenti to search database
    			checkIns[i] = response.getString(studentID); //puts Studenti in checkIns array
    		}
    		//build adapter
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, checkIns);
    		checkInsList = (ListView)findViewById(R.id.check_ins_list);
    		checkInsList.setAdapter(adapter);
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void getUsernameFromPrefs(){
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
    	usernameFromPref = userData.getString("username", "false");
    }
    
    private class PostWithAsync extends AsyncTask<String, String, String>{
    	private String URL;
    	private JSONObject sendJSONObject;
    	public PostWithAsync(String passedURL, JSONObject jo){
    		URL = passedURL;
    		sendJSONObject = jo;
    	}
    	
    	@Override
    	protected String doInBackground(String... arg0){
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
    	
    	protected void onPostExecute(String build){
    		JSONObject responseObject;
    		try{
    			responseObject = new JSONObject(build);
    			if(responseObject.has("status")){
    				Toast.makeText(InstructorCurrentCheckIns.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
    			}
    			else{
    				if(URL.equals(classListURL)) createClassListArrayFromServerResponse(responseObject);
    				else if(URL.equals(checkInsURL)) createCheckInsListArrayFromServerResponse(responseObject);
    			}
    		}
    		catch (JSONException e){
				e.printStackTrace();
			}
    	}
    }
    
    
}
