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
	private Button home, add;
	private EditText friend_username;
	private ListView friendList;
	private Intent homeIntent;
	private String username;
	private String studentFriendsURL = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/friendsPage";
	public static final String PREFS_NAME = "MyPreferencesFile";
	private JSONObject responseObject;
	private List<String> friends;
			
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_friends); //sets screen layout
		
		home = (Button)findViewById(R.id.home_button);
		add = (Button)findViewById(R.id.add_button);
		friend_username = (EditText)findViewById(R.id.editText_enterFriendName);
		
//		populateFriendListView();
//		registerClick();
		
		friendList = (ListView)findViewById(R.id.friendListView);
		new PostWithAsync().execute();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, friends);  
		
		friendList.setAdapter(adapter);
		registerClick();
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeIntent = new Intent(v.getContext(), StudentWelcome.class);
				goHome();
			} //end onClick(View v)
		});
		
//		add.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				username = friend_username.getText().toString();
//				JSONObject jo = createAddDeleteJSONObj(username, "add");
//				new PostWithAsync(studentFriendsURL, jo).execute();
//			}
//			
//		});
		
	} //end onCreate(Bundle savedInstanceState)
	
//	private void populateFriendListView() {
//		//Array
//		String[] f = {"Friend1", "Friend2", "Friend3"};
//		// Build adapter
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_items, f);
//		//ListView
//		ListView friends = (ListView)findViewById(R.id.friendListView);
//		friends.setAdapter(adapter);
//	}
	
	private void registerClick() {
		ListView friendList = (ListView)findViewById(R.id.friendListView);
		friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				TextView tv = (TextView) arg1;
				String message = tv.getText().toString();
				Toast.makeText(StudentFriends.this, message, Toast.LENGTH_LONG).show();
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
    
    private void goHome(){
    	startActivity(homeIntent);
    }
    
    private class PostWithAsync extends AsyncTask<String, String, String> {
//    	private String URL;
//    	private JSONObject sendJSONObject;
//		public PostWithAsync(String friendsURL, JSONObject jo) {
//			URL = friendsURL;
//			sendJSONObject = jo;
//		}

		@Override
		protected String doInBackground(String... arg0) {
		
			try{
				JSONObject jo = new JSONObject();
				jo.put("username", getUsername());
				
				JSONCommunication jc = new JSONCommunication();
				String build = jc.sendPost(studentFriendsURL, jo);
				
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
				friends = new ArrayList<String>();
				int size = responseObject.getInt("approveSize");
				for (int i=0; i<size; i++) {
					String friendName = "friendapproved";
					friendName = friendName.concat(Integer.toString(i+1));
					friends.add(responseObject.getString(friendName));
				}
				
	    		Toast.makeText(getApplicationContext(), responseObject.getString("status"), Toast.LENGTH_LONG).show();
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
    }
    
//    private JSONObject createAddDeleteJSONObj(String username, String editType) {
//    	JSONObject jobj = new JSONObject();
//		try {
//			jobj.put("username", username);
//			jobj.put("editType", editType);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//    	return jobj;
//    }
    
    
    private String getUsername() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("username", "false");
	}
    

}
