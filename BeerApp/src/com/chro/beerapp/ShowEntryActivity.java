package com.chro.beerapp;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.woodchro.bemystore.R;

public class ShowEntryActivity extends ActionBarActivity {

	Button btn_myEntry;
	Button btn_search;
	Button btn_openMap;
	Button btn_deleteFromFavorites;
	Button btn_addToFavorites;
	public Entry CurrentEntry;
	Entries RequestedList = Entries.getInstance();
	TextView Category;
	TextView PrdctName;
	TextView BegTime;
	TextView EndTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_entry);

		Intent intent = getIntent();
		int id = intent.getExtras().getInt("id");
		int adapterKind = intent.getExtras().getInt("adapterKind");
		switch (adapterKind) {
		case 0:
			CurrentEntry = RequestedList.MyEntries.get(id);
			break;
		case 1:
			CurrentEntry = RequestedList.Entries.get(id);
			break;
		case 2:
			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			CurrentEntry = db.getAllEntries().get(id);
		default:
			break;
		}
		//CurrentEntry = RequestedList.MyEntries.get(id);
		Category = (TextView) findViewById(R.id.txt_Category);
		Category.setText(CurrentEntry.getCategoryAsString());
		PrdctName = (TextView) findViewById(R.id.txt_PrdctName);
		PrdctName.setText(CurrentEntry.getProductName());
		BegTime = (TextView) findViewById(R.id.txt_timeBegin);
		BegTime.setText(CurrentEntry.getBeginTime().get(Calendar.HOUR_OF_DAY)
				+ ":" + CurrentEntry.getBeginTime().get(Calendar.MINUTE));
		EndTime = (TextView) findViewById(R.id.txt_timeEnd);
		EndTime.setText(CurrentEntry.getEndTime().get(Calendar.HOUR_OF_DAY)
				+ ":" + CurrentEntry.getEndTime().get(Calendar.MINUTE));
		/* @TODO Add Quantity and Price to the view */
		// CurrentEntry.getProductName();
		// CurrentEntry.getPrice();
		// CurrentEntry.getQuantity();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_Search));
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setTitle(CurrentEntry.getProductName());
		addListenerOnButton();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		btn_openMap = (Button) findViewById(R.id.Btn_OpenMap);
		btn_openMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String uri = String.format(Locale.ENGLISH,
						"geo:0,0?q=%f,%f(%s)", CurrentEntry.getLatitude(),
						CurrentEntry.getLongtitude(),
						CurrentEntry.getProductName());
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});
		
		btn_deleteFromFavorites = (Button) findViewById(R.id.Btn_deleteFromFavorites);
		btn_deleteFromFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				boolean isDeleted = db.deleteEntry(String.valueOf(CurrentEntry.getID()));
				db.close();
				if(isDeleted)Log.i("deleted","deleted");
				Intent intent = new Intent(getApplicationContext(),EntryFavoritesActivity.class);
				startActivity(intent);
			}
		});
		
		btn_addToFavorites = (Button) findViewById(R.id.Btn_addToFavorites);
		btn_addToFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				db.addEntry(CurrentEntry);
				db.close();
				Intent intent = new Intent(getApplicationContext(),EntryFavoritesActivity.class);
				startActivity(intent);
			}
		});

	}

}
