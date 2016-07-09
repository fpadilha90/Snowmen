package com.fpadilha.snowmen.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.activities.MainActivity;
import com.fpadilha.snowmen.adapters.SnowmanInfoWindowAdapter;
import com.fpadilha.snowmen.helpers.SnowmanHelper;
import com.fpadilha.snowmen.models.Snowman;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by felipe on 08/07/2016.
 */
@EFragment(R.layout.maps_fragment)
public class MapsFragment extends Fragment implements OnThread, OnMapReadyCallback {

    private MainActivity activity;
    private SupportMapFragment fragment;
    private GoogleMap map;
    private List<Snowman> snowmen;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = (MainActivity) getActivity();
        FragmentManager fm = getFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            fragment.getMapAsync(this);
        }
    }

    @Override
    @UiThread
    public void onThread(boolean onThread) {
        if (!onThread) {
            map.clear();
            fragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        map.getUiSettings().setAllGesturesEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(activity.currentLatitude, activity.currentLongitude), 15.0f));

        snowmen = SnowmanHelper.getAll(activity);

        int size = snowmen.size();
        for (int i = 0; i < size; i++) {
            Snowman snowman = snowmen.get(i);

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(snowman.getLatitude(), snowman.getLongitude()))
                    .title(snowman.getName()));

            marker.setSnippet(String.valueOf(i));

        }

        map.setInfoWindowAdapter(new SnowmanInfoWindowAdapter(activity, snowmen));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Snowman snowman = snowmen.get(Integer.parseInt(marker.getSnippet()));
                snowman.setFavorite(!snowman.isFavorite());

                SnowmanHelper.setFavoriteSnowman(activity, snowman);

                marker.showInfoWindow();

                activity.refreshLists(1);

            }
        });
    }
}
