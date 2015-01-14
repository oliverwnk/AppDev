package com.chro.beerapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ConnectionAdd extends AsyncTask<Void, String, String>{


	@Override
	protected String doInBackground(Void... params) {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("id", "12345"));

        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
       	String req = URLEncodedUtils.format(nameValuePairs, "utf-8");
       	
	    HttpGet httpget = new HttpGet("141.30.224.219/add"+req);
	
	    try {
	        // Add your data
	       
	
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httpget);
	        String responseStr = EntityUtils.toString(response.getEntity());
		    publishProgress(responseStr);
	        Log.i(responseStr,"|||||||||||||#########@#@#$@#$Q#|$#@|$#@|$");
	    } catch (ClientProtocolException e) {
	    	Log.i(e.toString(),e.toString());
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	
	    return "";
	}
	@Override
	protected void onPostExecute(String responseStr)
	{
		Entries e = Entries.getInstance();
		JSONObject j;
		try {
			j = new JSONObject(responseStr);
			e.addEntrie(j.getInt("categorie"), j.getString("productName"), (float)(j.getDouble("price")), j.getInt("quantity"), j.getString("contactDetails"), (float)j.getDouble("latitude"), (float)(j.getDouble("longtitude")), j.getString("beginTime"),j.getString("endTime"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//parse response string
		
	}

}
