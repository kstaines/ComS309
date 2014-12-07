package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminReset extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private static final String resetPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/resetPage";
	private Button home;
	private Button reset;
	private Intent homeIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_reset); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		reset = (Button)findViewById(R.id.reset_button);
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				homeIntent = new Intent(v.getContext(), AdminWelcome.class);
				goHome();
			} //end onClick(view v)
		});
		
		reset.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				v.setSelected(true);
		    	resetAlert();
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
    
    private void resetAlert(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminReset.this);
    	alertDialog.setTitle("Reset All");
    	alertDialog.setMessage("This will reset all student user points and class lists.\n" +
    			"Are you sure want to reset all?");
    	//on pressing no button
    	alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	//on pressing yes button
    	alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				resetAll();
			}
		});
    	alertDialog.show();
    }
    
    private void resetAll(){
    	JSONObject jo = new JSONObject();
    	new PostWithAsync(resetPageURL, jo).execute();
    }
    
    private class PostWithAsync extends AsyncTask<String, String, String>{
    	private String URL;
    	private JSONObject sendJSONObject;
    	public PostWithAsync(String resetURL, JSONObject jo){
    		URL = resetURL;
    		sendJSONObject = jo;
    	}
    	
    	protected String doInBackground(String... arg0){
    		try{
    			JSONCommunication jc = new JSONCommunication();
    			String build = jc.sendPost(URL,  sendJSONObject);
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
    		try{
    			responseObject = new JSONObject(build);
    			if(responseObject.getString("status").equalsIgnoreCase("true")){
    				Toast.makeText(AdminReset.this, "All student points and class lists have been reset", Toast.LENGTH_LONG).show();
    			}
    			else{
    				Toast.makeText(AdminReset.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();

    			}
    		}
    		catch (JSONException e) {
				e.printStackTrace();
			}
    	} //end onPostExecute(String build)
    } //end PostWithAsync extends AsyncTask<String, String, String>


}
