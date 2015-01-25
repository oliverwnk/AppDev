package com.chro.beerapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

public class ConnectionActivate extends AsyncTask<String, String, String>{
	
	
	Context context;
	ProgressDialog dialog;
	ConnectionActivate(Context context,ProgressDialog dialog)
	{
		this.dialog = dialog;
		this.context = context;
	}
	@Override
	protected String doInBackground(String... params) {
		// Create a new HttpClient and Post Header
	    String responseStr = "";
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        
	        nameValuePairs.add(new BasicNameValuePair("id", params[0]));
	       
	        HttpClient httpclient = new DefaultHttpClient();
	     	String req = URLEncodedUtils.format(nameValuePairs, "utf-8");
	     	 HttpGet httpget;
	     	 if(params[1].equals("true"))
	     	 {
	     		httpget= new HttpGet("http://141.30.224.219:5000/activate?" + req);
	     	 }else{
	     		httpget = new HttpGet("http://141.30.224.219:5000/deactivate?" + req);
	     	 }
	       // httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httpget);
	        responseStr = EntityUtils.toString(response.getEntity());
		    publishProgress(responseStr);
	    } catch (ClientProtocolException e) {
	    	Log.i(e.toString(),e.toString());
	    	return "";
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	       Log.i(e.toString(),"execpt"); // TODO Auto-generated catch block
	       return "";
	    }

	    return responseStr;
	}
	@Override
	protected void onPostExecute(String responseStr)
	{
		Entries e = Entries.getInstance();
		dialog.cancel();
		if(!responseStr.equals("ok"))
		{
	    	CharSequence text = "Error connecting please try again later";
	    	Toast t = Toast.makeText(context,text,Toast.LENGTH_LONG);
	    	t.show();
			return ;
		}

		
	}
	protected void onProgressUpdate()
	{
	}
}
