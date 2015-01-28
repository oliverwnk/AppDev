package com.chro.beerapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.woodchro.bemystore.R;

public class MapActivity extends ActionBarActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback,
		LocationListener, OnMapLoadedCallback {

	private GoogleMap map;
	GoogleApiClient mGoogleApiClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	GpsLocation gpsLocation;
	ArrayList<Entry> entries;
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

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		gpsLocation.isGooglePlayServicesAvailable();
		if (gpsLocation.isGpsEnabled()) {
			mGoogleApiClient.connect();
		} else {
			gpsLocation.showGpsAlertDialog();
		}
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

	// begin of functions for interfaces
	// After calling connect(), this method will be invoked asynchronously
	// when the connect request has successfully completed.
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		// notices when location changes
		// mLocationClient.requestLocationUpdates(mLocationRequest, this);
		// Location update
		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);

//		mCurrentLocation = LocationServices.FusedLocationApi
//		 .getLastLocation(mGoogleApiClient);
//		
//		 LatLng latLng = new LatLng(mCurrentLocation.getLatitude(),
//		 mCurrentLocation.getLongitude());
//		 CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
//		 15);
//		 map.moveCamera(cameraUpdate);
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

	// end of functions for interfaces

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_Map));
		supportActionBar.setDisplayHomeAsUpEnabled(true);

		gpsLocation = new GpsLocation(this, this);
		mGoogleApiClient = buildNewGoogleApiClient();
		entries = Entries.getInstance().Entries;

		 Intent intent = getIntent();

		
		 mCurrentLocation = intent
		 .getParcelableExtra(MainActivity.LAST_LOCATION);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		// A data object that contains quality of service parameters for
		// requests
		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		// enables my location and adds button for my location

		SharedPreferences prefs = getSharedPreferences("location",
				(MODE_PRIVATE));
		float Longitude = prefs.getFloat("Longitude", 0);
		float Latitude = prefs.getFloat("Latitude", 0);
		Log.i("aï¿½sdkfjawoeifj", String.valueOf(Longitude));
		// mCurrentLocation.setLatitude((double) Longitude);
		// mCurrentLocation.setLongitude((double) Latitude);

	}

	protected GoogleApiClient buildNewGoogleApiClient() {
		return new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	// tells LocationListener what to do when location changes
	public void onLocationChanged(Location location) {
		mCurrentLocation = location;

		// LatLng latLng = new LatLng(location.getLatitude(),
		// location.getLongitude());
		// CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
		// 15);
		// map.animateCamera(cameraUpdate);
		// locationManager.removeUpdates(this);
	}

	// display coordinates in textview
	// private void updateWithNewLocation(Location location) {
	// TextView myLocationText;
	// myLocationText = (TextView) findViewById(R.id.myLocationText);
	// String latLongString = "No location found";
	// if (location != null) {
	// double lat = location.getLatitude();
	// double lng = location.getLongitude();
	// latLongString = "Lat:" + lat + "\nLong:" + lng;
	// }
	// myLocationText.setText("Your Current Position is:\n" + latLongString);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.map, menu);
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

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		// set markers
		this.map = map;
		map.setOnMapLoadedCallback(this);

		//adds my location button to map
		map.setMyLocationEnabled(true);
//		LatLng latLng = new LatLng(mCurrentLocation.getLatitude(),
//				mCurrentLocation.getLongitude());
//		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
//				15);
//		map.moveCamera(cameraUpdate);

		

	}

	// sets marker from entry list and moves camera to see all markers
	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		LatLngBounds.Builder builder = new LatLngBounds.Builder();

		// hashmap to map MarkerId with position of entry in entrylist
		// to start right ShowEntryActivity when clicking on infoWindow
		final Map<String, Integer> markerToEntryMap = new HashMap<String, Integer>();
		int position = 0;
		for (Entry e : entries) {
			LatLng latlng = new LatLng(e.getLatitude(), e.getLongtitude());

			Marker marker = map.addMarker(new MarkerOptions()
					.position(latlng)
					.title(e.getProductName())
					.snippet(
							"Preis: " + String.valueOf(e.getPrice()) + " €   "
									+ "Menge: "
									+ String.valueOf(e.getQuantity())));
			markerToEntryMap.put(marker.getId(), position);
			Log.d("AAAAAA",
					String.valueOf(markerToEntryMap.get(marker.getId())));

			builder.include(latlng);
			marker.showInfoWindow();
			position++;
		}
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ShowEntryActivity.class);
				int markerPosition = markerToEntryMap.get(marker.getId());
				intent.putExtra("id", markerPosition);
				intent.putExtra("adapterKind", 1);
				startActivity(intent);
			}
		});
		LatLngBounds bounds = builder.build();
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,
				250);
		map.moveCamera(cameraUpdate);
	}

}
