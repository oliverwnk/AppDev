package com.chro.beerapp;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ShowEntryActivity extends Activity {

	Button btn_myEntry;
	Button btn_search;
	Entry CurrentEntry;
	Entries RequestetList = Entries.getInstance();
	TextView Categorie;
	TextView PrdctName;
	TextView BegTime;
	TextView EndTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_entry);
		Intent intent = getIntent();
		int id = intent.getExtras().getInt("id");
		CurrentEntry = RequestetList.getLastRequest().get(id);
		Categorie = (TextView) findViewById(R.id.txt_Categorie);
		Categorie.setText(CurrentEntry.getCategorie());
		PrdctName = (TextView) findViewById(R.id.txt_PrdctName);
		PrdctName.setText(CurrentEntry.getProductName());
		BegTime = (TextView) findViewById(R.id.txt_timeBegin);
		BegTime.setText(CurrentEntry.getBeginTime().get(Calendar.HOUR_OF_DAY)+":"+CurrentEntry.getBeginTime().get(Calendar.MINUTE));
		EndTime = (TextView) findViewById(R.id.txt_timeEnd);
		EndTime.setText(CurrentEntry.getEndTime().get(Calendar.HOUR_OF_DAY)+":"+CurrentEntry.getEndTime().get(Calendar.MINUTE));
		/*@TODO Add Quantity and Price to the view*/
		//CurrentEntry.getProductName();
		//CurrentEntry.getPrice();
		//CurrentEntry.getQuantity();
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	public void addListenerOnButton(){
			
	}
	

}
