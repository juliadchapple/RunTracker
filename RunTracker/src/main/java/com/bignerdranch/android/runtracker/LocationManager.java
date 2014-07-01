package com.bignerdranch.android.runtracker;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;


/**
 * Created by jchapple on 5/21/14.
 */
public class LocationManager implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {

    private static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * 1;
    private RunFragment mFragment;
    private static final String TAG = "LocationManager";

    public LocationClient mLocationClient;
    private Location mCurrentLocation;
    LocationRequest mLocationRequest;

    public LocationManager(Context context) {
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        mLocationClient = new LocationClient(context, this, this);
    }

    public void connect(){
        mLocationClient.connect();
    }

    public void disconnect(){
        mLocationClient.disconnect();
    }

    //mCurrentLocation = mLocationClient.getLastLocation();

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.i(TAG, "Location received: " + location.getLatitude() + ", " + location.getLongitude());
        mFragment.updateLocation(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected");
        //if updates enabled {
        //mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "Disconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    public void stopLocationUpdates() {
        if (mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(this);
        }
    }

    public void startLocationUpdates() {
        if (mLocationClient.isConnected()) {
            //if updates enabled {
            mLocationClient.requestLocationUpdates(mLocationRequest, this);
        }
    }

    public void setRunFragment(RunFragment fragment) {
        mFragment = fragment;
    }
}
