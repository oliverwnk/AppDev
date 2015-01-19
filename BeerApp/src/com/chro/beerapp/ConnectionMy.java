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

public class ConnectionMy extends AsyncTask<String, String, String>{
	
	
	Context context;
	ProgressDialog dialog;
	ConnectionMy(Context context, ProgressDialog dialog)
	{
		this.dialog=dialog;
		this.context = context;
	}
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
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        
        nameValuePairs.add(new BasicNameValuePair("user", params[0]));
  
    	String req = URLEncodedUtils.format(nameValuePairs, "utf-8");
	    HttpGet httpget = new HttpGet("http://141.30.224.219:5000/My?"+req);
	    String responseStr = "";
	    try {
	        // Add your data
	       
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
		JSONObject j;
		JSONArray array;
		dialog.cancel();
		if(responseStr.equals(""))
		{
	    	CharSequence text = "Error connecting please try again later";
	    	Toast t = Toast.makeText(context,text,Toast.LENGTH_LONG);
	    	t.show();
			return ;
		}
		try {
			responseStr = Html.fromHtml(responseStr).toString();
			array = new JSONArray(responseStr);
			if(array.length() > 0)
				e.MyEntries.clear();
			for(int n = 0; n < array.length();n++)
			{
				j = array.getJSONObject(n);
				e.addMyEntry(j.getString("categorie"), j.getString("productName"), (float)(j.getDouble("price")), j.getInt("quantity"), j.getString("contactDetails"), (float)j.getDouble("latitude"), (float)(j.getDouble("longtitude")), j.getString("beginTime"),j.getString("endTime"));
			}

			Intent i = new Intent(context, EntryActivity.class); 
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(i); 
			//context.startActivity(new Intent(context, EntryActivity.class));
			return;
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	    	CharSequence text = "Something somewhere went terribly wrong";
	    	Toast t = Toast.makeText(context,text,Toast.LENGTH_LONG);
	    	t.show();
			return ;
		}
		//parse response string
		
	}
	protected void onProgressUpdate()
	{
	}
}
