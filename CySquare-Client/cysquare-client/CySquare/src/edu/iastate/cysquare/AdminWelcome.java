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

public class AdminWelcome extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	
	private Button logout;
	private Button manageClasses;
	private Button reset;
	private Button manageUsers;
	
	private Intent mainIntent;
	private Intent manageClassesIntent;
	private Intent resetIntent;
	private Intent manageUsersIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_welcome);
		
		logout = (Button)findViewById(R.id.logout_button);
		manageClasses = (Button)findViewById(R.id.manage_classes_button);
		reset = (Button)findViewById(R.id.reset_all_button);
		manageUsers = (Button)findViewById(R.id.manage_users_button);
		
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
				mainIntent = new Intent(v.getContext(), MainActivity.class);
				clearUsername();
				logout();
			} //end onClick(View v)
		}); //end logout.setOnClickListener
		
		manageClasses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				manageClassesIntent = new Intent(v.getContext(), AdminClasses.class);
				manageClasses();
			} //end onClick(View v)
		}); //end manageClasses.setOnClickListener
		
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				resetIntent = new Intent(v.getContext(), AdminReset.class);
				reset();
			} //end onClick(View v)
		}); //end reset.setOnClickListener
		
		manageUsers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				manageUsersIntent = new Intent(v.getContext(), AdminUsers.class);
				manageUsers();
			} //end onClick(View v)
		}); //end manageUsers.setOnClickListener
		
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
    
    //clear username value to empty string
    private void clearUsername(){
		SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = userData.edit();
		editor.putString("username", "");
		editor.commit();
    }
    
    private void logout(){
    	startActivity(mainIntent);
    }
    
    private void manageClasses(){
    	startActivity(manageClassesIntent);
    }
    
    private void reset(){
    	startActivity(resetIntent);
    }
    
    private void manageUsers(){
    	startActivity(manageUsersIntent);
    }
}
