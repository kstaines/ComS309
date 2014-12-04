package edu.iastate.cysquare;

import java.io.IOException;

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

public class AdminUsers extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private static final String approvedURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/approvedList";
	private Button home;
	private Intent homeIntent;
	private ListView userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_users); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		
		populateUsersListView();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), AdminWelcome.class);
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

    private void populateUsersListView() {
    	JSONObject jo = new JSONObject();
    	new PostWithAsync(approvedURL, jo).execute();
    }
    
    private void createUserArray(JSONObject response) {
    	try {
			int size = response.getInt("size");
			String[] users = new String[size];
			for (int i=0; i<size; i++) {
				String name = "user";
				name = name.concat(Integer.toString(i+1));
				users[i] = response.getString(name);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, users);
			userList = (ListView)findViewById(R.id.approvedUsers);
			userList.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
	private void registerClick() {
		userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				String clickedUser = tv.getText().toString();
				Toast.makeText(AdminUsers.this, "User selected is " + clickedUser, Toast.LENGTH_LONG).show();
			}
		});
	}

    private class PostWithAsync extends AsyncTask<String, String, String> {
    	private String URL;
    	private JSONObject sendJSONObject;
		public PostWithAsync(String URL, JSONObject jo) {
			this.URL = URL;
			sendJSONObject = jo;
		}

		@Override
		protected String doInBackground(String... arg0) {
		
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
		
		protected void onPostExecute(String build) {
			JSONObject responseObject;
			try {
				responseObject = new JSONObject(build);
				if(responseObject.getString("status").equals("error")) {
					Toast.makeText(AdminUsers.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else {
					if (URL.equals(approvedURL)) createUserArray(responseObject);
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }

}
