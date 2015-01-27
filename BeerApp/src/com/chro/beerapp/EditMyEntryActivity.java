package com.chro.beerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRouter.SimpleCallback;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.cu;
import com.google.android.gms.location.LocationServices;
import com.woodchro.bemystore.R;

public class EditMyEntryActivity extends CreateEntryActivity {

	ArrayList<Entry> entryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		int id = intent.getExtras().getInt("id");
		
		entryList = Entries.getInstance().MyEntries;
		Entry currentEntry = entryList.get(id);
		
		EditText editProductName = (EditText)findViewById(R.id.editText3);
		editProductName.setText(currentEntry.getProductName());
		
		super.etextAmount.setText(String.valueOf(currentEntry.getQuantity()));
		super.etextPrice.setText(String.valueOf(currentEntry.getPrice()));
		
		Spinner spinnerCategory =(Spinner)findViewById(R.id.spinner1);
		spinnerCategory.setSelection(((ArrayAdapter)spinnerCategory.getAdapter()).getPosition(currentEntry.getCategory()));
		
		EditText editNumber = (EditText)findViewById(R.id.txt_Handy_Number);
		editNumber.setText(currentEntry.getContactDetails());
		
		TextView timeBegin = (TextView)findViewById(R.id.timeBegin);
		String timeBeginString = new SimpleDateFormat("HH:mm").format(currentEntry.getBeginTime().getTime());
		timeBegin.setText(timeBeginString);
		TextView timeEnd = (TextView)findViewById(R.id.timeEnd);
		String timeEndString = new SimpleDateFormat("HH:mm").format(currentEntry.getEndTime().getTime());
		timeEnd.setText(timeEndString);
	}
}