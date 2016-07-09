package com.fpadilha.snowmen.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.MainFragmentPagerAdapter;
import com.fpadilha.snowmen.services.AppRefreshTask;
import com.fpadilha.snowmen.services.TaskCallBack;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements TaskCallBack,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MainFragmentPagerAdapter adapter;

    @ViewById
    Toolbar toolbar;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TabLayout tabs;
    @ViewById
    ProgressBar progressBar;

    @NonConfigurationInstance
    public boolean onThread;

    @NonConfigurationInstance
    @Bean
    AppRefreshTask appRefreshTask;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    @NonConfigurationInstance
    public double currentLatitude;
    @NonConfigurationInstance
    public double currentLongitude;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


    }

    protected void onStart() {
        if (currentLatitude == 0 && currentLongitude == 0)
            mGoogleApiClient.connect();
        else
            fillAdapter();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @UiThread
    void fillAdapter() {
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void appRefresh() {
        if (!onThread) {

            setOnThread(true);
            appRefreshTask.start(this, currentLatitude, currentLongitude);
        }
    }

    public void refreshLists(int tab) {
        adapter.refreshFragments(tab);
    }

    public void refreshLists() {
        adapter.refreshFragments();
    }

    @UiThread
    void setOnThread(boolean onThread) {
        this.onThread = onThread;
        progressBar.setVisibility((onThread) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSuccess(String message) {
        setOnThread(false);
        fillAdapter();
    }

    @Override
    public void onFailed(String error) {
        setOnThread(false);
        fillAdapter();
        Snackbar.make(tabs, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setOnThread(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(tabs, getString(R.string.err_location_permission), Snackbar.LENGTH_LONG).show();
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            currentLatitude = mLastLocation.getLatitude();
            currentLongitude = mLastLocation.getLongitude();

            appRefresh();
        } else {
            setOnThread(false);
            Snackbar.make(tabs, getString(R.string.err_location_needed), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        setOnThread(false);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setOnThread(false);
        Snackbar.make(tabs, getString(R.string.err_location_needed), Snackbar.LENGTH_LONG).show();
    }
}
