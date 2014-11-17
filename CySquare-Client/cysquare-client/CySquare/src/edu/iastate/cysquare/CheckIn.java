package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;

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
import android.widget.Toast;

public class CheckIn extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private static final String checkinPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/checkIn";
	private static final String classListPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/classList";
	private Button home;
	private ListView classList;
	private Intent homeIntent;
	String usernameFromPref;
	String selectedClassName;
	String selectedSection;
	int totalNumOfClasses;
	private String[] courseInfo = new String[3]; //[totalNumOfClasses];
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_checkin); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		classList = (ListView)findViewById(R.id.checkin_classes_list);
		usernameFromPref = retrieveUsername();
		
		//new PostWithAsync().execute();
		String[] courseInfo = new String[] {"course1", "course2", "course3"};
		
		ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, courseInfo);
		classList.setAdapter(arrAdapter);
		
		classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				//ListView Clicked item index
				int itemPosition = position;
				//ListView Clicked item value
				String itemValue = (String)classList.getItemAtPosition(position);
				//show alert
				Toast.makeText(getApplicationContext(), "Position : "+itemPosition+" Listitem : "+itemValue,  Toast.LENGTH_LONG).show();
			} //end onItemClick(AdapterView<?>
		});
		
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
    
    private String retrieveUsername(){
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return usernameFromPref = userData.getString("username", "false");
    }
    
    //this PostWithAsync will retrieve class list to display
    private class PostWithAsync extends AsyncTask<String, String, String>{
    	@Override
    	protected String doInBackground(String... arg0){
    		 
    		 JSONObject jo = new JSONObject();
    		 try{
    			 //jo.put("username", usernameFromPref);
    			 //jo.put("classname", selectedClassName);
    			 //jo.put("section", selectedSection);
    			 JSONCommunication jc = new JSONCommunication();
    			 String build = jc.sendPost(classListPageURL, jo);
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
				totalNumOfClasses = responseObject.getInt("size");
				if(totalNumOfClasses==0){
					String noClassesError = responseObject.getString("error");
		    		Toast.makeText(getApplicationContext(), noClassesError, Toast.LENGTH_LONG).show();
				}
				
				
/*				for(int i=1; i<=totalNumOfClasses; i++){
					String courseNum = "Course" + i;
					//String courseFromJSON = responseObject.getString(courseNum);
					courseInfo[i-1] = responseObject.getString(courseNum);
				}*/
				
				String[] courseInfo = {"course1", "course2", "course3"};
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
    		
    		
    	} //end onPostExecute(String build)
    } //end PostWithAsync extends AsyncTask<String, String, String>

}
