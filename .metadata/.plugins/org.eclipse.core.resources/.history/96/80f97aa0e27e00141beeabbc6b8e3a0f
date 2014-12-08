package com.chro.beerapp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateEntryActivity extends ActionBarActivity {

	Button btn_CreateEntry;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		initTime();
		addListenerOnButton();
	}
	DatabaseHandler db = new DatabaseHandler(this);
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public void addListenerOnButton(){
		btn_CreateEntry = (Button) findViewById(R.id.btn_Save_New_Entry);
		btn_CreateEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = 1;
				Entry entry1 = new Entry(i,"entry"+i,i*0,67,"details",234,456,"2014-04-23 16:29","2014-04-23 18:29");
		       	db.addEntry(entry1);
				Intent intent = new Intent(getApplicationContext(),EntryActivity.class);
		        startActivity(intent);
			}
		});
	}
	
	public void initTime(){
		
	final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    Log.i("ausgabe minute", "minute=" + minute);
    

    
    TextView timeBegin=(TextView)findViewById(R.id.timeBegin);
    if(minute<10)timeBegin.setText(hour+":"+"0"+minute);
    else timeBegin.setText(hour+":"+minute);
	
	hour= hour+2;
	TextView timeEnd=(TextView)findViewById(R.id.timeEnd);
	if(minute<10)timeEnd.setText(hour+":"+"0"+minute);
    else timeEnd.setText(hour+":"+minute);
	
	
	}
    
}
