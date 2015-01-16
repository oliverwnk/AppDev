package com.chro.beerapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import com.woodchro.bemystore.R;

public class SettingsActivity extends ActionBarActivity {
	
	EditText etextName;
	EditText etextRadius;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		getSupportActionBar().setTitle("Optionen");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	
		//get shared preferences
		prefs = getSharedPreferences("settings", MODE_PRIVATE);
		editor = prefs.edit();
		
		//get radius value from sharedprefs
		etextRadius = (EditText)findViewById(R.id.etext_radius);
		int radius = prefs.getInt("radius", 0);
		etextRadius.setText(String.valueOf(radius));
		
		//get Name value from sharedprefs
		etextName = (EditText)findViewById(R.id.etxt_name);
		etextName.setText(prefs.getString("name", "unknown"));
		
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		editor.putInt("radius", Integer.parseInt(etextRadius.getText().toString()));
		editor.putString("name", etextName.getText().toString());
		editor.commit();
		
	}

	public void addListenerOnButton(){

	}
	

}
