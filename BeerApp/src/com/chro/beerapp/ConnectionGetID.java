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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ConnectionGetID extends AsyncTask<String, String, String>{

	Context context;
	ProgressDialog dialog;
	public ConnectionGetID(Context context, ProgressDialog dialog) {
		this.context = context;
		this.dialog = dialog;
	}

	@Override
	protected String doInBackground(String... params) {
		// Create a new HttpClient and Post Header
		
	    HttpClient httpclient = new DefaultHttpClient();
	    String responseStr = "fail";

       	
	    HttpGet httpget = new HttpGet("http://141.30.224.219:5000/getID");
	
	    try {
	        // Add your data
	       
	
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httpget);
	        responseStr = EntityUtils.toString(response.getEntity());
		    publishProgress(responseStr);
	    } catch (ClientProtocolException e) {
	    	Log.i(e.toString(),e.toString());
	    	return "";
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	return "";
	        // TODO Auto-generated catch block
	    }		
	
	    return responseStr;
	}
	@Override
	protected void onPostExecute(String responseStr)
	{
		try{
			SharedPreferences prefs = context.getSharedPreferences("user", context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt("id", Integer.parseInt(responseStr) );
			editor.commit();
			dialog.cancel();
		}catch(NumberFormatException e)
		{
			Intent i = new Intent(context, MainActivity.class); 
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(i); 
		}	
	}

}
