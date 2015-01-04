package com.chro.beerapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.R.string;
import android.os.AsyncTask;
import android.util.Log;

public class Connection extends AsyncTask<Void, String, String>{
	
	
	
	public String Search(int Categorie,float latiude,float longitude,int Radius) throws ClientProtocolException, IOException
	{
		return "";
		
	} 
/*
		HttpClient httpclient = new DefaultHttpClient();
	    HttpResponse response = httpclient.execute(new HttpGet("http://www.google.com"));
	    StatusLine statusLine = response.getStatusLine();
	    String responseString;
	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        responseString = out.toString();
	        out.close();
	        //..more logic
	    } else{
	        //Closes the connection.
	        response.getEntity().getContent().close();
	        throw new IOException(statusLine.getReasonPhrase());
	    }
		return responseString;
	}*/
	public List<String> Parse()
	{
		return null;
	}
	@Override
	protected String doInBackground(Void... params) {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet("http://www.google.com/");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	       // httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
		//parse response string
		e.addEntrie(0, "name", 0.1f, 1, "000112", 0.3f, 51.00f, "2014-04-23 16:29","2014-04-23 18:29");
	}
}
