package edu.iastate.cysquare;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminUnapprovedUsers extends ActionBarActivity {
	private static final String unapprovedURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/notApprovedList";
	private static final String manageUsersURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/manageUser";
	private ListView userList;
	private Button back, approve, delete;
	private String clickedUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_unapproved_users);
		
		populateUnapprovedUsersListView();
		registerClick();
		
		back = (Button)findViewById(R.id.back_button);
		approve = (Button)findViewById(R.id.approve_button);
		delete = (Button)findViewById(R.id.delete_button);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				Intent backIntent = new Intent(v.getContext(), AdminUsers.class);
		    	startActivity(backIntent);
			} //end onClick(view v)
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				JSONObject jo = new JSONObject();
				try {
					jo.put("username", clickedUser);
					jo.put("editType", "delete");
					new PostWithAsync(manageUsersURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Intent usersIntent = new Intent(v.getContext(), AdminUnapprovedUsers.class);
				startActivity(usersIntent);
			} //end onClick(view v)
		});
		
		approve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				JSONObject jo = new JSONObject();
				try {
					jo.put("username", clickedUser);
					jo.put("editType", "approve");
					new PostWithAsync(manageUsersURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				Intent usersIntent = new Intent(v.getContext(), AdminUnapprovedUsers.class);
				startActivity(usersIntent);
			} //end onClick(view v)
		});
		
	}

	private void populateUnapprovedUsersListView() {
		JSONObject jo = new JSONObject();
		new PostWithAsync(unapprovedURL, jo).execute();
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
			userList = (ListView)findViewById(R.id.unapprovedList);
			userList.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
   
	private void registerClick() {
		userList = (ListView)findViewById(R.id.unapprovedList);
		userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				clickedUser = tv.getText().toString();
				Toast.makeText(AdminUnapprovedUsers.this, clickedUser + " is selected", Toast.LENGTH_LONG).show();
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
					Toast.makeText(AdminUnapprovedUsers.this, responseObject.getString("error"), Toast.LENGTH_LONG).show();
				}
				else {
					if (URL.equals(unapprovedURL)) createUserArray(responseObject);
					else if (URL.equals(manageUsersURL)) Toast.makeText(AdminUnapprovedUsers.this, clickedUser + " has been approved/deleted", Toast.LENGTH_LONG).show();
				}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }

	
}
