package com.chro.beerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.woodchro.bemystore.R;

public class EntryActivity extends ActionBarActivity implements OnItemClickListener {

	private Button btn_CreateEntry;
	private ListView lst_Entry;
    ArrayList<Entry> listItems=Entries.getInstance().getEntriesByName("");
    //ArrayAdapter<String> adapter;
    //0 for MyEntries and 1 for Entrylist for Search
    int adapterKind=0; 
    Toolbar toolbar;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entries);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar_entries);
		
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		lst_Entry = (ListView) findViewById(R.id.Lst_Entries);
        lst_Entry.setOnItemClickListener(this);
		//adapter = new ArrayAdapter<String>(this, R.layout.list_item,listItems);
        
        //DatabaseHandler db = new DatabaseHandler(this);
        
        
        
		lst_Entry.setAdapter(new EntryAdapter(this, listItems, adapterKind));
		addListenerOnButton();
	}
	public void addListenerOnButton(){
		btn_CreateEntry = (Button) findViewById(R.id.btn_CreateEntry);
		btn_CreateEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),CreateEntryActivity.class);

				startActivity(intent);
			}
		});
	}
	/*public void addItem(String Item)
	{
		listItems.add(Item);
		adapter.notifyDataSetChanged();
	}*/
	@Override
	public void onItemClick(AdapterView<?> l, View v, int id, long pos) {
		Intent intent = new Intent(getApplicationContext(),ShowEntryActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
		Log.i("HelloListView", "You clicked Item: " + id + " at position:" + pos);
		
	}

	
}
