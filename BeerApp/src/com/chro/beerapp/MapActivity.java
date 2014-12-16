package com.chro.beerapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.woodchro.bemystore.R;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private GoogleMap map;
	LocationClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;

	// constants for Location Update Preferences
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Update frequency in milliseconds
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;

	
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
				mLocationClient.connect();
				break;
			}

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
				errorFragment.show(getFragmentManager(),
				 "Location Updates");
			}

			return false;
		}
	}

	
	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			mLocationClient.connect();
		}
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		// mLocationClient.disconnect();
		super.onStop();
	}

	// begin of functions for interfaces
	//After calling connect(), this method will be invoked asynchronously 
	//when the connect request has successfully completed.
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		//notices when location changes
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}

	// end of functions for interfaces

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// set mapFragement
		if (map == null) {
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}

		// locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		//
		// Criteria criteria = new Criteria();
		// criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// criteria.setPowerRequirement(Criteria.POWER_LOW);
		// criteria.setAltitudeRequired(false);
		// criteria.setBearingRequired(false);
		// criteria.setSpeedRequired(false);
		// criteria.setCostAllowed(true);
		//
		// String provider = locationManager.getBestProvider(criteria, true);
		// Location location = locationManager.getLastKnownLocation(provider);
		//
		// updateWithNewLocation(location);
		// LatLng latLng = new LatLng(location.getLatitude(),
		// location.getLongitude());
		// CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
		// 15);
		// map.animateCamera(cameraUpdate);
		// locationManager.removeUpdates(this);
		// locationManager.requestLocationUpdates(provider, MIN_TIME,
		// MIN_DISTANCE,
		// this);
		
		//A data object that contains quality of service parameters for requests
		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		// enables my location and adds button for my location
		map.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(this, this, this);
		// mCurrentLocation = mLocationClient.getLastLocation();

	}
	//tells LocationListener what to do when location changes
	public void onLocationChanged(Location location) {
		updateWithNewLocation(location);
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				15);
		map.animateCamera(cameraUpdate);
		//locationManager.removeUpdates(this);
	}

	
	 public void onProviderDisabled(String provider) {
	 }
	
	 public void onProviderEnabled(String provider) {
	 }
	
	 public void onStatusChanged(String provider, int status, Bundle extras) {
	 }
	
	 //display coordinates in textview
	 private void updateWithNewLocation(Location location) {
	 TextView myLocationText;
	 myLocationText = (TextView) findViewById(R.id.myLocationText);
	 String latLongString = "No location found";
	 if (location != null) {
	 double lat = location.getLatitude();
	 double lng = location.getLongitude();
	 latLongString = "Lat:" + lat + "\nLong:" + lng;
	 }
	 myLocationText.setText("Your Current Position is:\n" + latLongString);
	 }

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
