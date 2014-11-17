package edu.iastate.cysquare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentFriends extends Activity{
	private Button home, add, delete;
	private EditText friend_username;
	private Intent homeIntent;
	private String fusername, clickedFriend;
	private static final String studentFriendsURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/friendsList";
	private static final String friendsPageURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/friendsPage";
	public static final String PREFS_NAME = "MyPreferencesFile";
	private JSONObject responseObject;
			
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_friends); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		add = (Button)findViewById(R.id.add_button);
		delete = (Button)findViewById(R.id.delete_button);
		friend_username = (EditText)findViewById(R.id.editText_enterFriendName);
		
		populateFriendListView();
//		createFriendArray(null);
		registerClick();
		
//		friendList = (ListView)findViewById(R.id.friendListView);
//		new PostWithAsync().execute();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), StudentWelcome.class);
				goHome();
			} //end onClick(View v)
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fusername = friend_username.getText().toString();
				JSONObject jo = new JSONObject();
				try {
					jo.put("username", getUsername());
					jo.put("editType", "add");
					jo.put("friendname", fusername);
					new PostWithAsync(friendsPageURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JSONObject jo = new JSONObject();
				try {
					jo.put("username", getUsername());
					jo.put("editType", "delete");
					jo.put("friendname", clickedFriend);
					new PostWithAsync(friendsPageURL, jo).execute();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
			
		
	} //end onCreate(Bundle savedInstanceState)
	
	private void populateFriendListView() {
		//Array
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", getUsername());
			new PostWithAsync(studentFriendsURL, jo).execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private void createFriendArray(JSONObject response) {
		try {
			//APPROVED FRIENDS
			int size = response.getInt("approveSize");
			String[] friends = new String[size];
			for (int i=0; i<size; i++) {
				String friendName = "friendapproved";
				friendName = friendName.concat(Integer.toString(i+1));
				friends[i] = response.getString(friendName);
			}
//			
			String[] f = {"friend1", "friend 2", "friend3"};
			
			// Build adapter
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, friends);	//switch to friends for array from server
			//ListView
			ListView friendList = (ListView)findViewById(R.id.friendListView);
			friendList.setAdapter(adapter);
			
			//UNAPPROVED FRIENDS
			size = response.getInt("notApproveSize");
			String[] notFriends = new String[size];
			for (int i=0; i<size; i++) {
				String friendName = "friendnotapproved";
				friendName = friendName.concat(Integer.toString(i+1));
				notFriends[i] = response.getString(friendName);
			}
			
			String[] nf = {"friend4", "friend5", "friend6"};
			
			// Build adapter
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.listview_items, notFriends);	//switch to friends for array from server
			//ListView
			ListView notFriendList = (ListView)findViewById(R.id.notFriendsListView);
			notFriendList.setAdapter(adapter2);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void registerClick() {
		ListView friendList = (ListView)findViewById(R.id.friendListView);
		friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				clickedFriend = tv.getText().toString();
				Toast.makeText(StudentFriends.this, clickedFriend, Toast.LENGTH_LONG).show();
			}
		});
		
		ListView notFriendList = (ListView)findViewById(R.id.notFriendsListView);
		notFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				clickedFriend = tv.getText().toString();
				Toast.makeText(StudentFriends.this, clickedFriend, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void checkAddDeleteResponse(JSONObject response) {
		try {
			if (response.getString("status").equals("true")) {
				Toast.makeText(StudentFriends.this, "Add/Delete was successful", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(StudentFriends.this, response.getString("error"), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
    
    private void goHome(){
    	startActivity(homeIntent);
    }
    
    private class PostWithAsync extends AsyncTask<String, String, String> {
    	private String URL;
    	private JSONObject sendJSONObject;
		public PostWithAsync(String friendsURL, JSONObject jo) {
			URL = friendsURL;
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
					if (URL.equals(studentFriendsURL)) createFriendArray(responseObject);
					else if (URL.equals(friendsPageURL)) checkAddDeleteResponse(responseObject);
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
    
    private String getUsername() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("username", "false");
	}
    

}
