package com.chro.beerapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class SearchActivity extends Activity {

	Button btn_startSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	public void addListenerOnButton(){
		btn_startSearch  = (Button) findViewById(R.id.Btn_startSearch);
		btn_startSearch.setOnClickListener(new OnClickListener() {
			@Override
		    public void onClick(View v) {
		        Intent intent = new Intent(getApplicationContext(),StartSearchActivity.class);
		        startActivity(intent);
		        }
	    
		});
	}
	

}
