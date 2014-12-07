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
	private static final String manageUsersURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/manageUser";
	private Button home, viewUnapproved, delete;
	private ListView userList;
	private String clickedUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_users); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		viewUnapproved = (Button)findViewById(R.id.unapprovedUsers_button);
		delete = (Button)findViewById(R.id.delete_button);
		
		populateUsersListView();
		registerClick();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent homeIntent = new Intent(v.getContext(), AdminWelcome.class);
		    	startActivity(homeIntent);
			} //end onClick(view v)
		});
		
		viewUnapproved.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent unapprovedIntent = new Intent(v.getContext(), AdminUnapprovedUsers.class);
		    	startActivity(unapprovedIntent);
			} //end onClick(view v)
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JSONObject jo = new JSONObject();
				try {
					jo.put("username", clickedUser);
					jo.put("editType", "delete");
					new PostWithAsync(manageUsersURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Intent usersIntent = new Intent(v.getContext(), AdminUsers.class);
				startActivity(usersIntent);
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
    
    private void populateUsersListView() {
    	JSONObject jo = new JSONObject();
    	new PostWithAsync(approvedURL, jo).execute();
    }
    
    private void createUserArray(JSONObject response) {
    	try {
			int size = response.getInt("size");
			String[] users = new String[size];
			for (int i=0; i<size; i++) {
				String name = "User";
				name = name.concat(Integer.toString(i+1));
				users[i] = response.getString(name);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, users);
			userList = (ListView)findViewById(R.id.approvedList);
			userList.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
	private void registerClick() {
		userList = (ListView)findViewById(R.id.approvedList);
		userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				clickedUser = tv.getText().toString();
				Toast.makeText(AdminUsers.this, clickedUser + " is selected", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void manageUsersResponse() {
		Toast.makeText(AdminUsers.this, clickedUser + " has been deleted", Toast.LENGTH_LONG).show();
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
					else if (URL.equals(manageUsersURL)) manageUsersResponse();
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }

}
