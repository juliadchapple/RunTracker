package com.bignerdranch.android.runtracker;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jchapple on 5/19/14.
 */
public class RunFragment extends Fragment {
    private Button mStartButton;
    private Button mStopButton;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView mStartedTextView;
    private TextView mAltitudeTextView;
    private TextView mDurationTextView;
    private RunManager mRunManager;
    private Run mRun;
    private Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunManager = RunManager.get(this, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mRunManager.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRunManager.disconnect();
    }

    @Override
    public void onStop() {
        mRunManager.stopLocationUpdates();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        mStartedTextView = (TextView) view.findViewById(R.id.run_startedTextView);
        mLongitudeTextView = (TextView) view.findViewById(R.id.run_longitudeTextView);
        mLatitudeTextView = (TextView) view.findViewById(R.id.run_latitudeTextView);
        mAltitudeTextView = (TextView) view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView) view.findViewById(R.id.run_durationTextView);

        mStartButton = (Button) view.findViewById(R.id.run_startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mRunManager.startLocationUpdates();
                mRun = new Run();
                updateUI(true);
            }
        });
        mStopButton = (Button) view.findViewById(R.id.run_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mRunManager.startLocationUpdates();
                updateUI(false);
            }
        });

        return view;
    }

    private void updateUI(boolean started) {
        mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);
    }

    public void updateLocation(Location location) {
        if (mRun != null) {
            mStartedTextView.setText(mRun.getStartDate().toString());
        }
        int durationSeconds = 0;
        mLastLocation = location;
        if (mRun != null && mLastLocation != null) {
            durationSeconds = mRun.getDurationSeconds(mLastLocation.getTime());
            mLatitudeTextView.setText((Double.toString(mLastLocation.getLatitude())));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
        }
        mDurationTextView.setText(Run.formatDuration(durationSeconds));
    }

}
