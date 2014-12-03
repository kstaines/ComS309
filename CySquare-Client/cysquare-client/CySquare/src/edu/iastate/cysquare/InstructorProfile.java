package edu.iastate.cysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InstructorProfile extends Activity{
	public static final String PREFS_NAME = "MyPreferencesFile";
	private Button home, add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructor_profile);
		
		home = (Button)findViewById(R.id.home_button);
		add = (Button)findViewById(R.id.add_button);
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent homeIntent = new Intent(v.getContext(), InstructorWelcome.class);
				startActivity(homeIntent);
			} //end onClick(View v)
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(v.getContext(), CreateClass.class);
				startActivity(addIntent);
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
    
}
