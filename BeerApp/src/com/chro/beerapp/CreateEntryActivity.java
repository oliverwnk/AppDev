package com.chro.beerapp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.location.LocationServices;
import com.woodchro.bemystore.R;

public class CreateEntryActivity extends ActionBarActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	Button btn_CreateEntry;
	Toolbar toolbar;
	SeekBar sbarPrice;
	SeekBar sbarAmount;
	EditText etextPrice;
	EditText etextAmount;
	
	//Location
	GoogleApiClient mGoogleApiClient;
	LocationManager lm;
	Context mContext;
	GpsLocation gpsLocation;
	boolean gpsResult;
	private Location mCurrentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		initTime();
		addListenerOnButton();

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_CreateEntry));
		supportActionBar.setDisplayHomeAsUpEnabled(true);

		//Location
		mContext = this;
		gpsLocation = new GpsLocation(mContext, this);
		mGoogleApiClient = buildNewGoogleApiClient();
		gpsLocation.isGooglePlayServicesAvailable();
		if (gpsLocation.isGpsEnabled()) {
				mGoogleApiClient.connect();
			
		} else {
			gpsLocation.showGpsAlertDialog();
		}
		
		sbarPrice = (SeekBar) findViewById(R.id.sb_price);
		etextPrice = (EditText) findViewById(R.id.edit_price);

		sbarAmount = (SeekBar) findViewById(R.id.sb_amount);
		etextAmount = (EditText) findViewById(R.id.edit_amount);

		// connect seekbar with edittext for price
		sbarPrice.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// ---change the font size of the EditText---
				float value = (float) progress / 2;
				etextPrice.setText(String.valueOf(value) + "0");
				etextPrice.setSelection(etextPrice.getText().length());
			}
		});

		etextPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					// Update Seekbar value after entering a number
					// sbarPrice.setProgress(Integer.parseInt(s.toString()));
				} catch (Exception ex) {
				}
			}

		});

		sbarAmount.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// ---change the font size of the EditText---

				etextAmount.setText(String.valueOf(progress));
				etextAmount.setSelection(etextAmount.getText().length());
			}
		});

		etextAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					// Update Seekbar value after entering a number
					// sbarPrice.setProgress(Integer.parseInt(s.toString()));
				} catch (Exception ex) {
				}
			}

		});

	}

	DatabaseHandler db = new DatabaseHandler(this);

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public void addListenerOnButton() {
		btn_CreateEntry = (Button) findViewById(R.id.btn_Save_New_Entry);
		btn_CreateEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = 1;
				ConnectionAdd Request = new ConnectionAdd();
				TextView timeBegin = (TextView) findViewById(R.id.timeBegin);
				TextView endBegin = (TextView) findViewById(R.id.timeEnd);
				CheckBox retry = (CheckBox) findViewById(R.id.checkBox1);
				Spinner Kategorie = (Spinner) findViewById(R.id.spinner1);
				TextView productName = (TextView) findViewById(R.id.editText3);
				TextView price = (TextView) findViewById(R.id.edit_price);
				TextView amount = (TextView) findViewById(R.id.edit_amount);
				TextView contact = (TextView) findViewById(R.id.txt_Handy_Number);
				String s = String.valueOf(retry.isChecked());
				Request.execute("0", timeBegin.getText().toString(), endBegin
						.getText().toString(), Kategorie.getSelectedItem()
						.toString(), price.getText().toString(), amount
						.getText().toString(), contact.getText().toString(), s,
						productName.getText().toString());
				// Entry entry1 = new
				// Entry(i,"entry"+i,i*0,67,"details",234,456,"2014-04-23 16:29","2014-04-23 18:29");
				// db.addEntry(entry1);
				// Intent intent = new
				// Intent(getApplicationContext(),EntryActivity.class);
				// startActivity(intent);
			}
		});
	}

	public void initTime() {

		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		Log.i("ausgabe minute", "minute=" + minute);

		TextView timeBegin = (TextView) findViewById(R.id.timeBegin);
		if (minute < 10)
			timeBegin.setText(hour + ":" + "0" + minute);
		else
			timeBegin.setText(hour + ":" + minute);

		hour = hour + 2;
		TextView timeEnd = (TextView) findViewById(R.id.timeEnd);
		if (minute < 10)
			timeEnd.setText(hour + ":" + "0" + minute);
		else
			timeEnd.setText(hour + ":" + minute);

	}
	
	//Location
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		gpsLocation.isGooglePlayServicesAvailable();
		if (gpsLocation.isGpsEnabled()) {
				mGoogleApiClient.connect();
		} 
	}
	

	public GoogleApiClient buildNewGoogleApiClient() {
		return new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();

	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.

		
			if (mGoogleApiClient.isConnected()) {
				mGoogleApiClient.disconnect();
			}
					
		super.onStop();
	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case GpsLocation.CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				mGoogleApiClient.connect();
				break;
			}

		}
	}

	// After calling connect(), this method will be invoked asynchronously
	// when the connect request has successfully completed.
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status

		// notices when location changes
		// mLocationClient.requestLocationUpdates(mLocationRequest, this);
		
			mCurrentLocation = LocationServices.FusedLocationApi
					.getLastLocation(mGoogleApiClient);
			
			Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
			
	}

	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						GpsLocation.CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			showDialog(connectionResult.getErrorCode());
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}
	
	

}
