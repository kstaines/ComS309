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

public class InstructorWelcome extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	
	private Button logout;
	private Button myProfile;
	private Button currentCheckins;
	private Button postQuestion;
	private Button notifications;
	
	private Intent mainIntent;
	private Intent profileIntent;
	private Intent currentCheckinsIntent;
	private Intent postQuestionIntent;
	private Intent notificationsIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructor_welcome);
		
		logout = (Button)findViewById(R.id.logout_button);
		myProfile = (Button)findViewById(R.id.instructor_profile);
		currentCheckins = (Button)findViewById(R.id.instructor_current_checkins);
		postQuestion = (Button)findViewById(R.id.post_question_button);
		notifications = (Button)findViewById(R.id.notifications_button);
		
		logout.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
				mainIntent = new Intent(v.getContext(), MainActivity.class);
				clearUsername();
				logout();
			} //end onClick(View v)
		}); //end logout.setOnClickListener
		
		myProfile.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				profileIntent = new Intent(v.getContext(), InstructorProfile.class);
				profile();
			} //end onClick(View v)
		}); //end myProfile.setOnClickListener
		
		currentCheckins.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				currentCheckinsIntent = new Intent(v.getContext(), InstructorCurrentCheckIns.class);
				checkins();
			} //end onClick(View v)
		}); //end currentCheckins.setOnClickListener
		
		postQuestion.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				postQuestionIntent = new Intent(v.getContext(), InstructorPostQuestion.class);
				postQuestion();
			} //end onClick(View v)
		}); //end myProfile.setOnClickListener
		
		notifications.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				notificationsIntent = new Intent(v.getContext(), Notifications.class);
				notifications();
			} //end onClick(View v)
		}); //end myProfile.setOnClickListener
		
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
    
    //clear username value to empty string when user logs out
    private void clearUsername(){
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = userData.edit();
    	editor.putString("username", "");
    	editor.commit();
    }
    
    private void logout(){
    	startActivity(mainIntent);
    }
    
    private void profile(){
    	startActivity(profileIntent);
    }
    
    private void checkins(){
    	startActivity(currentCheckinsIntent);
    }
    
    private void postQuestion(){
    	startActivity(postQuestionIntent);
    }
    
    private void notifications(){
    	startActivity(notificationsIntent);
    }
}
