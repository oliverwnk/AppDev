package com.chro.beerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
// , LocationListener
{

	// key for putExtra
	public static final String LAST_LOCATION = "location";

	Button btn_myEntry;
	Button btn_search;
	Button btn_settings;
	Button btn_myFavorites;
	Button btn_map;
	GoogleApiClient mGoogleApiClient;
	LocationManager lm;
	Context mContext;
	GpsLocation gpsLocation;
	boolean gpsResult;

	private Location mCurrentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int id = prefs.getInt("id", -1);
		if(id == -1)
		{
			
			ProgressDialog dialog = ProgressDialog.show(this, "Getting ID", "Please wait...", true);
			ConnectionGetID getID = new ConnectionGetID(this, dialog);
			getID.execute();
			//editor.putFloat("id",  );
		}
		editor.commit();
		mContext = this;
		gpsLocation = new GpsLocation(mContext, this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_entries);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		
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
	//

	public void addListenerOnButton() {
		btn_myEntry = (Button) findViewById(R.id.btn_MyEntry);
		btn_myEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = ProgressDialog.show(mContext, "Getting Your entries", "Please wait...", true);
				Intent intent = new Intent(getApplicationContext(),
						EntryActivity.class);
				SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				int id = prefs.getInt("id", -1);
				editor.commit();
				ConnectionMy my = new ConnectionMy(getApplicationContext(),dialog);
				my.execute(String.valueOf(id));
				//startActivity(intent);
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
				intent.putExtra(LAST_LOCATION, mCurrentLocation);
				ProgressDialog dialog = ProgressDialog.show(mContext, "Getting Your entries", "Please wait...", true);
				SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				int id = prefs.getInt("id", -1);
				editor.commit();
				prefs = getSharedPreferences("settings", MODE_PRIVATE);
				Integer radius = prefs.getInt("radius", 0);
				if(mCurrentLocation == null)
				{
					startActivity(intent);
				}
				ConnectionSearch search = new ConnectionSearch(getApplicationContext(),dialog,MapActivity.class);
				search.execute("",String.valueOf(mCurrentLocation.getLongitude()),String.valueOf(mCurrentLocation.getLatitude()),radius.toString(),"","");
				//startActivity(intent);
			}

		});
		
		btn_myFavorites = (Button) findViewById(R.id.Btn_MyFavorites);
		btn_myFavorites.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EntryFavoritesActivity.class);
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
			if (mCurrentLocation != null)
				saveLocationToSharedPrefs();
		
		super.onStop();
	}

	public void saveLocationToSharedPrefs() {
		SharedPreferences prefs = getSharedPreferences("location", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putFloat("Longitude", (float) mCurrentLocation.getLongitude());
		editor.putFloat("Latitude", (float) mCurrentLocation.getLatitude());
		editor.commit();
		float Longitude = prefs.getFloat("Longitude", 0);
		Log.i("preferecnes", String.valueOf(Longitude));
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
