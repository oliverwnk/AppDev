package com.chro.beerapp;

import java.security.PublicKey;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.woodchro.bemystore.R;

public class GpsLocation{
	
	
	// methods for location awareness
		public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
		Location mCurrentLocation;
		Context mContext;
		LocationManager mLocationManager;
		Activity mActivity;
		
		public GpsLocation(Context context, Activity activity){
			this.mContext=context;
			this.mActivity = activity;
		}
		
		// check if gps is enabled
		public boolean isGpsEnabled() {
			mLocationManager = (LocationManager) mActivity.getSystemService(android.content.Context.LOCATION_SERVICE);
			return (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
					|| mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
		}
		
		public void showGpsAlertDialog(){
				AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
				dialog.setMessage(mActivity.getString(R.string.gps_network_not_enabled));
				dialog.setPositiveButton(
						mActivity.getString(R.string.open_location_settings),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub
								Intent myIntent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								mContext.startActivity(myIntent);
								
								// get gps
							}
						});
				dialog.setNegativeButton(mActivity.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface paramDialogInterface,
									int paramInt) {
								// TODO Auto-generated method stub
								
							}
						});
				dialog.show();
		}

		// Google Play Services check
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

		public boolean isGooglePlayServicesAvailable() {
			// Check that Google Play services is available
			int resultCode = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(mContext);
			// If Google Play services is available
			if (ConnectionResult.SUCCESS == resultCode) {
				// In debug mode, log the status
				Log.d("Location Updates", "Google Play services is available.");
				return true;
			} else {
				// Get the error dialog from Google Play services
				Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, mActivity, CONNECTION_FAILURE_RESOLUTION_REQUEST);

				// If Google Play services can provide an error dialog
				if (errorDialog != null) {
					// Create a new DialogFragment for the error dialog
					ErrorDialogFragment errorFragment = new ErrorDialogFragment();
					errorFragment.setDialog(errorDialog);
					errorFragment.show(mActivity.getFragmentManager(), "Location Updates");
				}

				return false;
			}
			
		}
}
			
			
		


		
