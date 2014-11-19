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

public class Notifications extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	String usertypeFromPrefs;
	private Button home;
	private Intent studentHomeIntent;
	private Intent instructorHomeIntent;
	private Intent adminHomeIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications);
		
		home = (Button)findViewById(R.id.home_button);
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				studentHomeIntent = new Intent(v.getContext(), StudentWelcome.class);
				instructorHomeIntent = new Intent(v.getContext(), InstructorWelcome.class);
				adminHomeIntent = new Intent(v.getContext(), AdminWelcome.class);
				getUsertype();
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
    
    private void getUsertype(){
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		usertypeFromPrefs = userData.getString("usertype", "false");
    }
    
    private void goHome(){
    	if(usertypeFromPrefs.equalsIgnoreCase("student")){
    		startActivity(studentHomeIntent);
    	}
    	else if(usertypeFromPrefs.equalsIgnoreCase("instructor")){
    		startActivity(instructorHomeIntent);
    	}
    	else if(usertypeFromPrefs.equalsIgnoreCase("admin")){
    		startActivity(adminHomeIntent);
    	}
    	else{
    		Toast.makeText(getApplicationContext(), "Could not get usertype from preferences file", Toast.LENGTH_LONG).show();
    	}
    }

}
