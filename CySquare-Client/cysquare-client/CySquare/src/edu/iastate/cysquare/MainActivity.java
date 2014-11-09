package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
	public static final String PREFS_NAME = "MyPreferencesFile";

	private EditText username;
	private EditText password;
	private Button login, createNewUser;
	private Intent welcomeIntent, createIntent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //next two lines take care of NetworkOnMainThreadException
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setContentView(R.layout.activity_main); //sets screen layout
        username = (EditText)findViewById(R.id.editText_username); 
        password = (EditText)findViewById(R.id.editText_password);
        login = (Button)findViewById(R.id.button_login); 
        createNewUser = (Button) findViewById(R.id.button_Create);
        
        
        login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				welcomeIntent = new Intent(v.getContext(), StudentWelcome.class);
				new PostWithAsync().execute();
			} //end onClick(View v)
		});
        
        createNewUser.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v) {
            	createIntent = new Intent(v.getContext(), CreateUser.class);
            	startActivity(createIntent);
        	}
        });
    }
      	
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

    
    private class PostWithAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			String url = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/login";
//			String url = "http://10.24.84.79:8081/login";		// Local server used for debugging
	    	
	    	//Create message
	    	JSONObject jo = new JSONObject();	
	    	try{
				jo.put("username", username.getText().toString());
				jo.put("password", password.getText().toString());
				
				//Send message and get response
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
			
				if(responseObject.getString("status").equals("true")){ //login info was correct/true
		    		Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
		    		saveUsername();
		    		startActivity(welcomeIntent);
		    	}
		    	else {
		    		Toast.makeText(getApplicationContext(), responseObject.getString("status"), Toast.LENGTH_LONG).show();
		    	}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
    
    private void saveUsername() {
    	
		SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = userData.edit();
		editor.putString("username", username.getText().toString());
		editor.commit();
    }
    
	
}//end MainActivity
