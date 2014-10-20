package edu.iastate.cysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentWelcome extends Activity{
	private Button logout;
	private Intent mainIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_welcome); //sets screen layout
		
		logout = (Button)findViewById(R.id.logout_button);
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
				mainIntent = new Intent(v.getContext(), MainActivity.class);
				logout();
				
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
    
    private void logout(){
    	startActivityForResult(mainIntent, 0);
    }

}
