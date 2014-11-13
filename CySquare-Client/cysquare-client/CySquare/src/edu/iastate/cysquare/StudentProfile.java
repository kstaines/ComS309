package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentProfile extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private Button home;
	private TextView totalPointsServer, classPoints;
	private Intent homeIntent;
	private static final String profilePageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/profilePage";
	String usernameFromPref;
	int totalPointsFromJSON;
	Handler myHandler;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_profile); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		totalPointsServer = (TextView)findViewById(R.id.total_points_server);
		classPoints = (TextView)findViewById(R.id.textView_class_points);
		
		usernameFromPref = retrieveUsername();	//get username from preferences file
		
		printTotalPoints();
				
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
    
    private void goHome(){
    	startActivity(homeIntent);
    }
    
    //gets username from preferences file, if username does not exist, returns "false"
    private String retrieveUsername() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return usernameFromPref = userData.getString("username", "false");
	}
    
    private void printTotalPoints() {	
    	new PostWithAsync().execute();
    }

    private class PostWithAsync extends AsyncTask<String, String, String>{
    	@Override
    	protected String doInBackground(String... arg0){
    		 
    		 JSONObject jo = new JSONObject();
    		 try{
    			 jo.put("username", usernameFromPref);
    			 JSONCommunication jc = new JSONCommunication();
    			 String build = jc.sendPost(profilePageURL, jo);
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
				totalPointsFromJSON = responseObject.getInt("points");
				totalPointsServer.setText(Integer.toString(totalPointsFromJSON));
				
				String classPointsList = "";
				int total = responseObject.getInt("total");
				for (int i=0; i<total; i++){
					String courseKey = "course";
					int courseKeyNumber = i+1;
					courseKey = courseKey.concat(Integer.toString(courseKeyNumber));
					if (i==0){
						classPointsList = classPointsList.concat(responseObject.getString(courseKey));
					}
					else {
						classPointsList = classPointsList.concat("\n" + responseObject.getString(courseKey));
					}
				}
				classPoints.setText(classPointsList.toString());
			} 
    		catch (JSONException e) {
				e.printStackTrace();
			}
    		
    	} //end onPostExecute(String build)
    } //end PostWithAsync extends AsyncTask<String, String, String>
}



