package com.bignerdranch.android.runtracker;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;


/**
 * Created by jchapple on 5/19/14.
 */
public class RunManager {

    private static final String TAG = "RunManager";

    public static final String ACTION_LOCATION =
            "com.bignerdranch.android.runtracker.ACTION_LOCATION";

    private static RunManager sRunManager;
    private Context mAppContext;
    private static LocationManager mLocationManager;

    private RunManager(RunFragment fragment, Context appContext) {
        mAppContext = appContext;

        int resultCode = GooglePlayServicesUtil.
                            isGooglePlayServicesAvailable(mAppContext);
        if (ConnectionResult.SUCCESS != resultCode) {
            Log.e(TAG, "Play service connection failed: " + resultCode);
            /*
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getSupportFragmentManager(),
                        "Location Updates");
            }*/
            return;
        }

        mLocationManager = new LocationManager(appContext);
        mLocationManager.setRunFragment(fragment);
    }

    public static RunManager get(RunFragment fragment, Context c) {
        if (sRunManager == null) {
            sRunManager = new RunManager(fragment, c.getApplicationContext());
        }
        return sRunManager;
    }

    public void connect() {
        mLocationManager.connect();
    }

    public void disconnect() {
        mLocationManager.disconnect();
    }

    public void startLocationUpdates() {
        mLocationManager.startLocationUpdates();
    }

    public void stopLocationUpdates() {
        mLocationManager.stopLocationUpdates();
    }

}
