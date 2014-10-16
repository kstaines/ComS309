package com.example.myfirstapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

   private EditText  username=null;
   private EditText  password=null;
   private TextView attempts;
   private Button login;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      username = (EditText)findViewById(R.id.username_input_field);
      password = (EditText)findViewById(R.id.password_input_field);
      login = (Button)findViewById(R.id.log_in_button);
   }

   public void login(View view){
      if(username.getText().toString().equals("admin") && 
    	  password.getText().toString().equals("admin")){

    	  Toast.makeText(getApplicationContext(), "Logging in...", 
    	  Toast.LENGTH_SHORT).show();
    	     
    	  setContentView(R.layout.student_profile);    	  
    	  //StudentProfile studentProfile = new StudentProfile();
    	  //studentProfile.mainPage();
      }	
      
	   else{
	      Toast.makeText(getApplicationContext(), "Wrong Credentials",
	      Toast.LENGTH_SHORT).show();
	      attempts.setBackgroundColor(Color.RED);	
	   }

}
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}