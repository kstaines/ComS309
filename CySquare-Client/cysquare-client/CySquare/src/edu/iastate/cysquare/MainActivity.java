package edu.iastate.cysquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

public class MainActivity extends ActionBarActivity{
	private EditText username;
	private EditText password;
	private Button login;
	private HttpClient http;
	private HttpPost request;
	private HttpResponse response;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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

				new PostWithAsync().execute();
				
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

    
    private class PostWithAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			System.out.println(arg0);
			
			String url = "http://proj-309-w03.cs.iastate.edu/cysquare-web-1.0.0-SNAPSHOT/login";
//			String url = "http://10.24.84.109:8081/login";		// Local server used for debugging
//			String url = "http://192.168.1.17:8081/login";
			
			http = new DefaultHttpClient();
	    	HttpConnectionParams.setConnectionTimeout(http.getParams(), 100000); //Timeout Limit
	    	
	    	//Create message
	    	JSONObject jo = new JSONObject();	
	    	try{
				jo.put("username", username.getText().toString());
				jo.put("password", password.getText().toString());
				
				//Send message and get response
				String build = sendPost(url, jo);
				
				publishProgress();
				
				return build;
	    	}
	    	catch (JSONException e){
				e.printStackTrace();
			}
	    	catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    	catch (ClientProtocolException e) {
				e.printStackTrace();
			}
	    	catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String build) {
			JSONObject responseObject;
			try {
				responseObject = new JSONObject(build);
			
				if(responseObject.getString("status").equals("true")){ //login info was correct/true
		    		Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
		    	}
		    	else if (!responseObject.getBoolean("status")) {
		    		System.out.println(responseObject.toString());
		    		Toast.makeText(getApplicationContext(), "Status False", Toast.LENGTH_LONG).show();
		    	}
		    	else
		    	{
//		    		Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
		    		Toast.makeText(getApplicationContext(), responseObject.toString(), Toast.LENGTH_LONG).show();
		    	}
			}
	    	catch (JSONException e){
				e.printStackTrace();
			}
		}
		
		protected final void publishProgress(String string) {
    		Toast.makeText(getApplicationContext(), "Logging In . . .", Toast.LENGTH_LONG).show();
		}
    }
    
	private String sendPost(String url, JSONObject jo) throws ClientProtocolException, IOException, JSONException {
		
		StringEntity mySE = new StringEntity(jo.toString());
		mySE.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded; charset=UTF-8")); //setContentType sets content type of the response being sent to the client
		request = new HttpPost(url);
		HttpParams params = new BasicHttpParams();
		params.setParameter("username", jo.getString("username")).setParameter("password", jo.getString("password"));
		request.setParams(params);
		request.setEntity(mySE);

		
		System.out.println(jo.toString());
		System.out.println(request.getParams().getParameter("password"));
		System.out.println(request.getParams().getParameter("username"));
		
		response = http.execute(request);
					
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String build = reader.readLine();
		return build;
	}
}//end MainActivity
