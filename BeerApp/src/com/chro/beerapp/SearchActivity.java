package com.chro.beerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.woodchro.bemystore.R;

public class SearchActivity extends ActionBarActivity implements
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {

	Button btn_startSearch;
	
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
		setContentView(R.layout.search);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_Search));
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
		
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		btn_startSearch = (Button) findViewById(R.id.Btn_startSearch);

		btn_startSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectionSearch SearchDatabase = new ConnectionSearch(getApplicationContext());

				EditText text = (EditText) findViewById(R.id.editText1);
				Spinner spin = (Spinner) findViewById(R.id.spinner1);
				SharedPreferences prefs = getSharedPreferences("location",
						(MODE_PRIVATE));
				Float Longitude = prefs.getFloat("Longitude", 0);
				Float Latitude = prefs.getFloat("Latitude", 0);	
				prefs = getSharedPreferences("settings", MODE_PRIVATE);
				Integer radius = prefs.getInt("radius", 0);
				String i = text.getText().toString();
				//{
					//SearchDatabase.execute(text.getText().toString(), spin.getSelectedItem().toString(),Longitude.toString(),Latitude.toString(),radius.toString());

				//}else{
					SearchDatabase.execute(text.getText().toString(), spin.getSelectedItem().toString(),Longitude.toString(),Latitude.toString(),radius.toString(),text.getText().toString());
				//}
				Intent intent = new Intent(getApplicationContext(),
						EntrySearchActivity.class);
				
				//startActivity(intent);
			}

		});
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
