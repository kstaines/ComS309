package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

public class CheckIn extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private static final String checkinPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/checkIn";
	private static final String classListPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private Button home;
	private ListView classList;
	private Intent homeIntent;
	private String usernameFromPref;
	private String selectedClassName;
	private String selectedSection;
	private String clickedClass;
	private double onCampusMaxLat = 42.035704;
	private double onCampusMinLat = 42.022784;
	private double onCampusMaxLong = -93.633409;
	private double onCampusMinLong = -93.654311;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_checkin); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		classList = (ListView)findViewById(R.id.checkin_classes_list);
		usernameFromPref = retrieveUsername();


		//populate checkin class list
		populateCheckInListView();
		
		if(locationCheck()){
			//register click on class
			registerClick();
		}
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), StudentWelcome.class);
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
    
    private void populateCheckInListView(){
    	JSONObject jo = new JSONObject();
    	try{
    		jo.put("username", usernameFromPref);
    		new PostWithAsync(classListPageURL, jo).execute();
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void createCheckInArrayFromServerResponse(JSONObject responseObject){
    	try{
    		int size = responseObject.getInt("size");
    		String[] classes = new String[size]; //stores class straight from server
    		String[] modClasses = new String[size]; //stores split up class
    		String[] simpleClass = new String[size]; //stores class info, no "labels"
    		for (int i=0; i<size; i++){
    			String courseID = "Course";
    			courseID = courseID.concat(Integer.toString(i+1)); //creates Coursei to search database
    			classes[i] = responseObject.getString(courseID); //puts Coursei in classes array
    			modClasses = classes[i].split(" "); //splits up each class string retrieved from server
    			String classDept = modClasses[2]; //grabs second element, the class
    			String classSec = modClasses[4];
    			String location = modClasses[6];
    			String days = modClasses[8];
    			String time = modClasses[10];
    			
    			//put all wanted elements together to get rid of all "labels"
    			simpleClass[i] = classDept + " " + classSec + " " + location + " " + days + " " + time;
    		}
    		
    		//build adapter
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, simpleClass);
    		//ListView
    		classList = (ListView)findViewById(R.id.checkin_classes_list);
    		
    		classList.setAdapter(adapter);
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void registerClick(){
    	ListView classList = (ListView)findViewById(R.id.checkin_classes_list);
    	classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> arg0, View v, int position, long id){
    			TextView tv = (TextView) v;
    			clickedClass = tv.getText().toString();
    			
    			String splitClassString[] = clickedClass.split(" ");
				selectedClassName = splitClassString[0]; //grabs 0th element, class name
				selectedSection = splitClassString[1]; //grabs 1st element, class section
    			Toast.makeText(CheckIn.this, "Class selected: " + selectedClassName + " Section: " + selectedSection, Toast.LENGTH_LONG).show();

				JSONObject jo = new JSONObject();
				try{
	    			//Toast.makeText(CheckIn.this, "TEST", Toast.LENGTH_LONG).show();

					jo.put("username", usernameFromPref);
					jo.put("classname", selectedClassName);
					jo.put("section", selectedSection);
					new PostWithAsync(checkinPageURL, jo).execute();
				}
				catch(JSONException e){
					e.printStackTrace();
				}
    		}
		});
    } //end registerClick
    
    private void checkInClass(JSONObject responseObject){
    	try{
    		if(responseObject.getString("status").equals("true")){
    			Toast.makeText(CheckIn.this, "You have been checked in!", Toast.LENGTH_LONG).show();
    		}
    		else{
    			Toast.makeText(CheckIn.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
    		}
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    }
    
    private void goHome(){
    	startActivity(homeIntent);
    }
    
    private boolean locationCheck(){
    	boolean status = false;
    	GPSTracker gps = new GPSTracker(this);
    	
    	//getting latitude and longitude
    	double latitude = gps.getLatitude(); //returns latitude
    	double longitude = gps.getLongitude(); //returns longitude
    	
    	//register click on campus
    	if(gps.canGetLocation()){
    	   	//gps is enabled
    		if(latitude <= onCampusMaxLat && latitude >= onCampusMinLat
    				&& longitude <= onCampusMaxLong && longitude >= onCampusMinLong){
    			//student is within campus latitude and longitude
    			Toast.makeText(this, "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_LONG).show();
    								
    			status = true;
    		}
    		else{
    			Toast.makeText(this, "You are not on campus.", Toast.LENGTH_LONG).show();
    			status = false;
    		}
    	}
    	else{
    		//gps not enabled
    		//show settings alert dialog
    		gps.showSettingsAlert();
    		locationCheck(); //recursive call
    	}
    		
    	//stop using gps
    	gps.stopUsingGPS();
		return status;
    }
    
    private String retrieveUsername(){
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return usernameFromPref = userData.getString("username", "false");
    }
    
    //this PostWithAsync will retrieve class list to display
    private class PostWithAsync extends AsyncTask<String, String, String>{
    	private String URL;
    	private JSONObject sendJSONObject;
    	public PostWithAsync(String classesURL, JSONObject jo){
    		URL = classesURL;
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
 	    	catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
 			}
 	    	catch (IOException e) {
 				e.printStackTrace();
 			} 
    		
			return null;
    	} //end String doInBackground(String... arg0)
    	
    	protected void onPostExecute(String build){
    		JSONObject responseObject;
    		try {
				responseObject = new JSONObject(build);
				if(responseObject.has("status") && responseObject.getString("status").equals("error")){
					Toast.makeText(CheckIn.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else{
					if(URL.equals(classListPageURL)){
						Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
						createCheckInArrayFromServerResponse(responseObject);
					}
					else if(URL.equals(checkinPageURL)){
						Toast.makeText(getApplicationContext(), "111111111111", Toast.LENGTH_LONG).show();
						checkInClass(responseObject);
					}
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
    		
    		
    	} //end onPostExecute(String build)
    } //end PostWithAsync extends AsyncTask<String, String, String>
    
}
