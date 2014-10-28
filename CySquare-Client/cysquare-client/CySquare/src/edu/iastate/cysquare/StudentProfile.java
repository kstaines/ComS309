package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
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
import android.widget.Button;
import android.widget.TextView;

public class StudentProfile extends Activity{
	private Button home;
	private TextView totalPointsServer;
	public static final String PREFS_NAME = "MyPreferencesFile";
	private HttpClient http;
	private HttpPost request;
	private HttpResponse response;
	private Intent homeIntent;
	private static final String profilePageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/profilePage";
	String usernameFromPref;
	int totalPointsFromJSON;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_profile); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		totalPointsServer = (TextView)findViewById(R.id.total_points_server);
		
		//get username from preferences file
		usernameFromPref = retrieveUsername();
		
		new PostWithAsync().execute();
		
		//for testing: printing a string to a screen
		//String textFromJSON = "ThisTextIsTestText";
		
		totalPointsServer.setText(totalPointsFromJSON + "");
		
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
    
    private class PostWithAsync extends AsyncTask<String, String, String>{
    	@Override
    	protected String doInBackground(String... arg0){
    		 http = new DefaultHttpClient();
    		 HttpConnectionParams.setConnectionTimeout(http.getParams(), 100000); //Timeout Limit
    		 
    		 JSONObject jo = new JSONObject();
    		 try{
    			 jo.put("username", "usernameFromPref");
    			 JSONCommunication jc = new JSONCommunication();
    			 String build = jc.sendPost(http, request, response, profilePageURL, jo);
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
    	} //end String doInBackground(String... arg0)
    	
    	protected void onPostExecute(String build){
    		JSONObject responseObject;
    		try {
				responseObject = new JSONObject(build);
				totalPointsFromJSON = responseObject.getInt("points");
				//totalPointsFromJSON = 100; // <--for testing
			} 
    		catch (JSONException e) {
				e.printStackTrace();
			}
    		
    	} //end onPostExecute(String build)
    } //end PostWithAsync extends AsyncTask<String, String, String>
}



