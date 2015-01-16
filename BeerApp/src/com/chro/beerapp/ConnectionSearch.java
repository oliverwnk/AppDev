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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

public class ConnectionSearch extends AsyncTask<String, String, String>{
	
	
	
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
	protected String doInBackground(String... params) {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet("http://141.30.224.219:5000");
	    String responseStr = "";
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        
	        nameValuePairs.add(new BasicNameValuePair("description", params[0]));
	        nameValuePairs.add(new BasicNameValuePair("categorie", params[1]));
	        nameValuePairs.add(new BasicNameValuePair("longtitude", params[2]));
	        nameValuePairs.add(new BasicNameValuePair("latitude", params[3]));
	        nameValuePairs.add(new BasicNameValuePair("radius", params[4]));

	       // httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httpget);
	        responseStr = EntityUtils.toString(response.getEntity());
		    publishProgress(responseStr);
	    } catch (ClientProtocolException e) {
	    	Log.i(e.toString(),e.toString());
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

	    return responseStr;
	}
	@Override
	protected void onPostExecute(String responseStr)
	{
		Entries e = Entries.getInstance();
		JSONObject j;
		JSONArray array;
		try {
			responseStr = Html.fromHtml(responseStr).toString();
			array = new JSONArray(responseStr);
			e.Del();
			for(int n = 0; n < array.length();n++)
			{
				j = array.getJSONObject(n);
				e.addEntrie(j.getInt("categorie"), j.getString("productName"), (float)(j.getDouble("price")), j.getInt("quantity"), j.getString("contactDetails"), (float)j.getDouble("latitude"), (float)(j.getDouble("longtitude")), j.getString("beginTime"),j.getString("endTime"));
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//parse response string
		
	}
	protected void onProgressUpdate()
	{
	}
}
