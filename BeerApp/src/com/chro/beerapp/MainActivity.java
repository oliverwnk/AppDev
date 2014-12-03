package com.chro.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	Button btn_myEntry;
	Button btn_search;
	Button btn_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		if(toolbar != null){
			setSupportActionBar(toolbar);
		}
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	public void addListenerOnButton(){
			btn_myEntry  = (Button) findViewById(R.id.btn_MyEntry);
			btn_myEntry.setOnClickListener(new OnClickListener() {
				@Override
			    public void onClick(View v) {
			        Intent intent = new Intent(getApplicationContext(),EntryActivity.class);
			        startActivity(intent);
			        }
		    
			});
			btn_search  = (Button) findViewById(R.id.Btn_Search);
			btn_search.setOnClickListener(new OnClickListener() {
				@Override
			    public void onClick(View v) {
			        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
			        startActivity(intent);
			        }
		    
			});
			btn_settings  = (Button) findViewById(R.id.Btn_Settings);
			btn_settings.setOnClickListener(new OnClickListener() {
				@Override
			    public void onClick(View v) {
			        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
			        startActivity(intent);
			        }
		    
			});
	}
	

}
