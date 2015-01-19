package com.chro.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.internal.bt;
import com.woodchro.bemystore.R;

public class EntrySearchActivity extends EntryActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.adapterKind = 1;
		super.onCreate(savedInstanceState);
		
		//change btn_create to "Suchen"
		getSupportActionBar().setTitle("Anzeigen");
		btn_CreateEntry = (Button) findViewById(R.id.btn_CreateEntry);
		btn_CreateEntry.setVisibility(View.GONE);
	}
	
	@Override
	public void addListenerOnButton(){
		Button btn_CreateEntry = (Button) findViewById(R.id.btn_CreateEntry);
		btn_CreateEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//finish activity to get back to searchActiviy
				finish();
			}
		});
	}
}
