package com.chro.beerapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.woodchro.bemystore.R;

public class MainActivity extends ActionBarActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener
		//, LocationListener
		{
	
	//key for putExtra
	public static final String LAST_LOCATION = "location";


	
	Button btn_myEntry;
	Button btn_search;
	Button btn_settings;
	Button btn_map;
	GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		addListenerOnButton();
		
		//get location
		isGooglePlayServicesAvailable();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(LocationServices.API).build();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		btn_myEntry = (Button) findViewById(R.id.btn_MyEntry);
		btn_myEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EntryActivity.class);
				startActivity(intent);
			}

		});
		btn_search = (Button) findViewById(R.id.Btn_Search);
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SearchActivity.class);
				startActivity(intent);
			}

		});

		btn_map = (Button) findViewById(R.id.Btn_Map);
		btn_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MapActivity.class);
				intent.putExtra(LAST_LOCATION,mCurrentLocation);
				startActivity(intent);
			}

		});

		btn_settings = (Button) findViewById(R.id.Btn_Settings);
		btn_settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SettingsActivity.class);
				startActivity(intent);
			}

		});
	}
	
	// methods for location awareness
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	Location mCurrentLocation;
	
	//Google Play Services check
	// Define a DialogFragment that displays the error dialog
		public static class ErrorDialogFragment extends DialogFragment {

			// Global field to contain the error dialog
			private Dialog mDialog;

			// Default constructor. Sets the dialog field to null
			public ErrorDialogFragment() {
				super();
				mDialog = null;
			}

			// Set the dialog to display
			public void setDialog(Dialog dialog) {
				mDialog = dialog;
			}

			// Return a Dialog to the DialogFragment.
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				return mDialog;
			}
		}
		
		private boolean isGooglePlayServicesAvailable() {
			// Check that Google Play services is available
			int resultCode = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(this);
			// If Google Play services is available
			if (ConnectionResult.SUCCESS == resultCode) {
				// In debug mode, log the status
				Log.d("Location Updates", "Google Play services is available.");
				return true;
			} else {
				// Get the error dialog from Google Play services
				Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

				// If Google Play services can provide an error dialog
				if (errorDialog != null) {
					// Create a new DialogFragment for the error dialog
					ErrorDialogFragment errorFragment = new ErrorDialogFragment();
					errorFragment.setDialog(errorDialog);
					errorFragment.show(getFragmentManager(), "Location Updates");
				}

				return false;
			}
		}

		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// Decide what to do based on the original request code
			switch (requestCode) {

			case CONNECTION_FAILURE_RESOLUTION_REQUEST:
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
			Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
			// notices when location changes
			// mLocationClient.requestLocationUpdates(mLocationRequest, this);			
			
			mCurrentLocation = LocationServices.FusedLocationApi
					.getLastLocation(mGoogleApiClient);
			Toast.makeText(this, String.valueOf(mCurrentLocation.getLatitude()), Toast.LENGTH_SHORT).show();
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
							CONNECTION_FAILURE_RESOLUTION_REQUEST);
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
		
		/*
		 * Called when the Activity becomes visible.
		 */
		@Override
		protected void onStart() {
			super.onStart();
			// Connect the client.
		//	if (isGooglePlayServicesAvailable()) {
				mGoogleApiClient.connect();

		//	}
		}

		/*
		 * Called when the Activity is no longer visible.
		 */
		@Override
		protected void onStop() {
			// Disconnecting the client invalidates it.
			mGoogleApiClient.disconnect();
			SharedPreferences prefs = getSharedPreferences("location", MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putFloat("Longitude", (float)mCurrentLocation.getLongitude());
			editor.putFloat("Latitude", (float)mCurrentLocation.getLatitude());
			editor.commit();
			float Longitude = prefs.getFloat("Longitude", 0);
			Log.i("preferecnes",String.valueOf(Longitude));
			super.onStop();
		}
}
