package edu.iastate.cysquare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentWelcome extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	
	private Button logout;
	private Button checkIn;
	private Button myFriends;
	private Button myProfile;
	private Button myClasses;
	
	private Intent mainIntent;
	private Intent checkInIntent;
	private Intent friendsIntent;
	private Intent profileIntent;
	private Intent classesIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_welcome); //sets screen layout
		
		logout = (Button)findViewById(R.id.logout_button);
		checkIn = (Button)findViewById(R.id.check_in_button);
		myFriends = (Button)findViewById(R.id.my_friends_button);
		myProfile = (Button)findViewById(R.id.my_profile_button);
		myClasses = (Button)findViewById(R.id.my_classes_button);
		
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
				mainIntent = new Intent(v.getContext(), MainActivity.class);
				
				//clear username value to empty string
				SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = userData.edit();
				editor.putString("username", "");
				editor.commit();
				
				logout();
			} //end onClick(View v)
		}); //end logout.setOnClickListener
		
		checkIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				checkInIntent = new Intent(v.getContext(), CheckIn.class);
				checkIn();
			} //end onClick(View v)
		}); //end checkIn.setOnClickListener
		
		myFriends.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				friendsIntent = new Intent(v.getContext(), StudentFriends.class);
				friends();
			} //end onClick(View v)
		}); //end myFriends.setOnClickListener
		
		myProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				profileIntent = new Intent(v.getContext(), StudentProfile.class);
				studentProfile();
			} //end onClick(View v)
		}); //end myProfile.setOnClickListener
		
		myClasses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				classesIntent = new Intent(v.getContext(), StudentClasses.class);
				studentClasses();
			} //end onClick(View v)
		}); //end myClasses.setOnClickListener
		
		
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
    
    private void logout(){
    	startActivity(mainIntent);
    }
    
    private void checkIn(){
    	startActivity(checkInIntent);
    }
    
    private void friends(){
    	startActivity(friendsIntent);
    }
    
    private void studentProfile(){
    	startActivity(profileIntent);
    }
    
    private void studentClasses(){
    	startActivity(classesIntent);
    }

}
