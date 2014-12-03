package edu.iastate.cysquare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class CreateClass extends ActionBarActivity {
	public static final String PREFS_NAME = "MyPreferencesFile";
	private Button back;
//	private TimePicker timepicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_class);
		
		back = (Button)findViewById(R.id.button_back);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent backIntent;
				String usertype = getUserType();
				if (usertype.equalsIgnoreCase("admin")) {
					backIntent = new Intent(v.getContext(), AdminClasses.class);
					startActivity(backIntent);
				}
				else if (usertype.equalsIgnoreCase("instructor")) {
					backIntent = new Intent(v.getContext(), InstructorProfile.class);
					startActivity(backIntent);
				}
				
			} //end onClick(View v)
		}); //end back.setOnClickListener
		
	}
	
    //gets username from preferences file, if username does not exist, returns "false"
    private String getUserType() {
    	SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
		return userData.getString("usertype", "false");
	}


}
