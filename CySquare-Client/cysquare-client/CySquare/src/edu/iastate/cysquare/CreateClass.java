package edu.iastate.cysquare;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class CreateClass extends ActionBarActivity {
	
	private Button back;
	private TimePicker timepicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_class);
		
		back = (Button)findViewById(R.id.button_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// GET USERTYPE AND FIGURE OUT WHICH INTENT/ACTIVITY TO RETURN TO
				
				
			} //end onClick(View v)
		}); //end back.setOnClickListener
		
//		timepicker = (TimePicker) findViewById(R.id.timePicker1);
//        timepicker.setIs24HourView(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_class, menu);
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
