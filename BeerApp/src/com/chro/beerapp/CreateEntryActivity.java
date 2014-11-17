package com.chro.beerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateEntryActivity extends Activity {

	Button btn_CreateEntry;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		//addListenerOnButton();
	}
	public void addListenerOnButton(){
		//btn_CreateEntry = (Button) findViewById(R.id.);
		//btn_CreateEntry.setOnClickListener(new OnClickListener() {
		//	@Override
		//	public void onClick(View v) {
				
		//	}
		//});
	}
}
