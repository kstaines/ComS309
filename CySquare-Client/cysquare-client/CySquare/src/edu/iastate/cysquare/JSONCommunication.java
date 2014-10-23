package edu.iastate.cysquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.cysquare.domain.Parameter;

public class JSONCommunication {
	
	public String sendPost(HttpClient http, HttpPost request, HttpResponse response, String url, JSONObject jo) throws JSONException, IllegalStateException, IOException{
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		Iterator<String> keys = jo.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			Parameter param = new Parameter();
			param.setName(key);
			param.setValue(jo.getString(key));
			parameters.add(param);
		}
		StringEntity mySE = new UrlEncodedFormEntity(parameters);
		mySE.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;")); //setContentType sets content type of the response being sent to the client
		request = new HttpPost(url);
		request.setEntity(mySE);

		response = http.execute(request);
					
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String build = reader.readLine();
		return build;
	}

}
