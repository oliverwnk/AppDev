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

public class ConnectionAdd extends AsyncTask<String, String, String>{

	Context context;
	ProgressDialog dialog;
	public ConnectionAdd(Context context, ProgressDialog dialog) {
	
		this.context = context;
		this.dialog=dialog;
	}

	@Override
	protected String doInBackground(String... params) {
		// Create a new HttpClient and Post Header
		
	    HttpClient httpclient = new DefaultHttpClient();
	    String responseStr = "fail";
	    //Request.execute(timeBegin.toString(),endBegin.toString(), Kategorie.toString(), price.toString(), amount.toString(),contact.toString(),retry.toString());

	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user", params[0]));

        nameValuePairs.add(new BasicNameValuePair("beginTime",	params[1]));
        nameValuePairs.add(new BasicNameValuePair("endTime",  	params[2]));
        nameValuePairs.add(new BasicNameValuePair("categorie",	params[3]));
        nameValuePairs.add(new BasicNameValuePair("price", 		params[4]));
        nameValuePairs.add(new BasicNameValuePair("quantity", 	params[5]));
        nameValuePairs.add(new BasicNameValuePair("contactdetails", 	params[6]));
        nameValuePairs.add(new BasicNameValuePair("retry", 		params[7]));
        nameValuePairs.add(new BasicNameValuePair("productName", 		params[8]));
        nameValuePairs.add(new BasicNameValuePair("longtitude", 		params[9]));
        nameValuePairs.add(new BasicNameValuePair("latitude", 		params[10]));
        if(!params[11].equals(""))
        {
            nameValuePairs.add(new BasicNameValuePair("id", 		params[11]));
        }
        nameValuePairs.add(new BasicNameValuePair("text", 		"jojo"));
       	String req = URLEncodedUtils.format(nameValuePairs, "utf-8");
       	
	    HttpGet httpget = new HttpGet("http://141.30.224.219:5000/add?"+req);
	
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
		Entries e = Entries.getInstance();
		dialog.cancel();
		if(responseStr.equals("ok"))
		{
			//Intent i = new Intent(context, EntryActivity.class); 
			//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			//context.startActivity(i); 
			//context.startActivity(new Intent(context, EntryActivity.class));
			CharSequence text = "Successfully added an Entry";
	    	Toast t = Toast.makeText(context,text,Toast.LENGTH_LONG);
	    	t.show();
	    	ConnectionMy my = new  ConnectionMy(context,dialog);
	    	SharedPreferences prefs = context.getSharedPreferences("user", context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			Integer id = Integer.valueOf(prefs.getInt("id", -1));
			editor.commit();
	    	my.execute(String.valueOf(id));
			return;
		}else{
	    	CharSequence text = "Error connecting please try again later";
	    	Toast t = Toast.makeText(context,text,Toast.LENGTH_LONG);
	    	t.show();
		}
		//parse response string
		
	}

}
