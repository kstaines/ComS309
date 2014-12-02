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
	private JSONObject responseObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_checkin); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		classList = (ListView)findViewById(R.id.checkin_classes_list);
		usernameFromPref = retrieveUsername();
		
		//populate checkin class list
		populateCheckInListView();
		registerClick();

		//all this is in registerClick();
//		classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//				JSONObject jo = new JSONObject();
//				String splitClassString[] = clickedClass.split(" ");
//				String classDept = splitClassString[3];
//				String classNum = splitClassString[4];
//				String classSec = splitClassString[6];
//				String className = classDept + " " + classNum;
//				try{
//					jo.put("username", usernameFromPref);
//					jo.put("classname", className);
//					jo.put("section", classSec);
//					new PostWithAsync(checkinPageURL, jo).execute();
//				}
//				catch(JSONException e){
//					e.printStackTrace();
//				}
//				
//			} //end onItemClick(AdapterView<?>
//		});
		
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
    		//Toast.makeText(getApplicationContext(), size, Toast.LENGTH_SHORT).show();
    		
    		String[] classes = new String[size];
    		for (int i=0; i<size; i++){
    			String courseID = "Course";
    			courseID = courseID.concat(Integer.toString(i+1));
    			classes[i] = responseObject.getString(courseID);
    			//Toast.makeText(getApplicationContext(), classes[i], Toast.LENGTH_LONG).show();
    		}
    		
    		//build adapter
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, classes);
    		//ListView
//    		ListView classList = (ListView)findViewById(R.id.checkin_classes_list);
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
    			Toast.makeText(CheckIn.this, "Class selected is " + clickedClass, Toast.LENGTH_LONG).show();
    			
    			String splitClassString[] = clickedClass.split(" ");
				String classDept = splitClassString[3];
				String classNum = splitClassString[4];
				String classSec = splitClassString[6];
				String className = classDept + " " + classNum;
				JSONObject jo = new JSONObject();
				try{
					jo.put("username", usernameFromPref);
					jo.put("classname", className);
					jo.put("section", classSec);
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
    		if(responseObject.getString("Status").equals("true")){
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
