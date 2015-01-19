package com.chro.beerapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.woodchro.bemystore.R;

public class SearchActivity extends ActionBarActivity {

	Button btn_startSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_Search));
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		btn_startSearch = (Button) findViewById(R.id.Btn_startSearch);

		btn_startSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectionSearch SearchDatabase = new ConnectionSearch(getApplicationContext());

				EditText text = (EditText) findViewById(R.id.editText1);
				Spinner spin = (Spinner) findViewById(R.id.spinner1);
				SharedPreferences prefs = getSharedPreferences("location",
						(MODE_PRIVATE));
				Float Longitude = prefs.getFloat("Longitude", 0);
				Float Latitude = prefs.getFloat("Latitude", 0);	
				prefs = getSharedPreferences("settings", MODE_PRIVATE);
				Integer radius = prefs.getInt("radius", 0);
				SearchDatabase.execute(text.getText().toString(), spin.getSelectedItem().toString(),Longitude.toString(),Latitude.toString(),radius.toString());

				Intent intent = new Intent(getApplicationContext(),
						EntrySearchActivity.class);
				
				//startActivity(intent);
			}

		});
	}

}
