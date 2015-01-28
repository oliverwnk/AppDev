package com.chro.beerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.woodchro.bemystore.R;

public class ShowEntryActivity extends ActionBarActivity implements OnItemClickListener {

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
	TextView otherEntriesFromUser;
	TextView price;
	TextView amount;
	TextView number;
	
	ListView listView;
	
	int adapterKind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_entry);

		Intent intent = getIntent();
		int id = (int)intent.getExtras().getLong("id");
		if(id == 0)
			id = (int)intent.getExtras().getInt("id");
		adapterKind = intent.getExtras().getInt("adapterKind");
		int userId = intent.getExtras().getInt("userId");
		
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		btn_deleteFromFavorites = (Button) findViewById(R.id.Btn_deleteFromFavorites);
		btn_addToFavorites = (Button) findViewById(R.id.Btn_addToFavorites);
		listView = (ListView) findViewById(R.id.listview_showEntry);
		listView.setVerticalScrollBarEnabled(false);
		listView.setOnItemClickListener(this);
		
		
		//loading entryList depending on adpaterKind--> 0=MyEntries,1=Searched Entries, 2=Favorites
		//to get the right element/position from intent.extra
		
		switch (adapterKind) {
		case 0:
			CurrentEntry = RequestedList.MyEntries.get(id);
			break;
		case 1:
			if(userId==0){
			CurrentEntry = RequestedList.Entries.get(id);
			}else{
				CurrentEntry = RequestedList.getEntriesByUserID(userId).get(id);
			}
			otherEntriesFromUser = (TextView) findViewById(R.id.txt_otherEntriesFromUsers);
			otherEntriesFromUser.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			
			ArrayList<Entry> listItems;
			listItems = Entries.getInstance().getEntriesByUserID(CurrentEntry.getUser_id());
			//listItems.remove(CurrentEntry);
			if(listItems.isEmpty())otherEntriesFromUser.setText("No other entries offered by this User");
			listView.setAdapter(new EntryAdapter(this, listItems, adapterKind));
			break;
		case 2:
			CurrentEntry = db.getAllEntries().get(id);
			TextView dateTextView = (TextView)findViewById(R.id.txt_date);
			dateTextView.setVisibility(View.VISIBLE);
			dateTextView.setText("Entry from " + db.getDate(CurrentEntry));
		default:
			break;
		}
		
		
		//show addToFavorite or deleteFromFavorite button depending if entry exists in database
		if(db.exists(CurrentEntry))btn_deleteFromFavorites.setVisibility(View.VISIBLE);
		else{
			btn_addToFavorites.setVisibility(View.VISIBLE);
		}
		
		//CurrentEntry = RequestedList.MyEntries.get(id);
		Category = (TextView) findViewById(R.id.txt_Category);
		Category.setText(CurrentEntry.getCategoryAsString());
		PrdctName = (TextView) findViewById(R.id.txt_PrdctName);
		PrdctName.setText(CurrentEntry.getProductName());
		BegTime = (TextView) findViewById(R.id.txt_timeBegin);
		BegTime.setText(CurrentEntry.getBeginTime().get(Calendar.HOUR_OF_DAY)
				+ ":" + String.format("%02d",CurrentEntry.getBeginTime().get(Calendar.MINUTE)));
		EndTime = (TextView) findViewById(R.id.txt_timeEnd);
		EndTime.setText(CurrentEntry.getEndTime().get(Calendar.HOUR_OF_DAY)
				+ ":" + String.format("%02d",CurrentEntry.getEndTime().get(Calendar.MINUTE)));
		price = (TextView)findViewById(R.id.txt_PriceShowEntry);
		price.setText(String.valueOf(CurrentEntry.getPrice())+"0 €");
		amount = (TextView)findViewById(R.id.txt_AmountShowEntry);
		amount.setText(String.valueOf(CurrentEntry.getQuantity()));
		number = (TextView)findViewById(R.id.txt_numberShowEntry);
		number.setText(String.valueOf(CurrentEntry.getContactDetails()));

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
		
		
		btn_deleteFromFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				boolean isDeleted = db.deleteEntry(String.valueOf(CurrentEntry.getID()));
				db.close();
				if(isDeleted)Log.i("deleted","deleted");
				Intent intent = new Intent(getApplicationContext(),EntryFavoritesActivity.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "Entry is deleted from Favorites", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		btn_addToFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
				db.addEntry(CurrentEntry,date);
				db.close();
				Toast.makeText(getApplicationContext(), "Entry is added from Favorites", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),ShowEntryActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("adapterKind", adapterKind);
		intent.putExtra("userId", CurrentEntry.getUser_id());
		startActivity(intent);
		finish();
		
	}

}
