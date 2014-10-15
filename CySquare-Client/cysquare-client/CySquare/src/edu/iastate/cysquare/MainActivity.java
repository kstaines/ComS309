package edu.iastate.cysquare;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ActionBarActivity{
	private EditText username;
	private EditText password;
	private Button login;
//	private HttpClient http;
//	private HttpPost request;
//	private HttpResponse response;
	
	Context context;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = this;
        
        //next two lines take care of NetworkOnMainThreadException
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.editText_username);
        password = (EditText)findViewById(R.id.editText_password);
        login = (Button)findViewById(R.id.button_login);  
        
        login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

//				String url = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/login";
				String url = "http://10.24.46.97:8081/login";		// Local server used for debugging
				
	    	JSONObject jo = new JSONObject();	
	    	
				try
				{
					jo.put("username", username.getText().toString());
					jo.put("password", password.getText().toString());
					
					sendPost(url, jo);
					
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}//////////////////////////////////end onClick(View v)

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
    
	private String sendPost(String url, JSONObject jo) throws ClientProtocolException, IOException, JSONException {
		
		RequestQueue myRQ = Volley.newRequestQueue(this);
		JsonObjectRequest myJOR = new JsonObjectRequest(Request.Method.POST, url, jo,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						try {
							if(response.getString("status").equals("true")){ //login info was correct/true
					    		Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
					    	}
					    	else if (!response.getBoolean("status")) {
					    		Toast.makeText(getApplicationContext(), "Status False", Toast.LENGTH_LONG).show();
					    	}
					    	else
					    	{
					    		Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
					    	}
						}
				    	catch (JSONException e){
							e.printStackTrace();
						}
					}
		},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(context, error.toString() , Toast.LENGTH_SHORT).show();
					error.printStackTrace();
				}
			});
		
		myRQ.add(myJOR);
		
		return null;
	}

}//end MainActivity
