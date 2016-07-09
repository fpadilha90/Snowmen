package com.fpadilha.snowmen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.SnowmanInfoWindowAdapter;
import com.fpadilha.snowmen.helpers.SnowmanHelper;
import com.fpadilha.snowmen.models.Snowman;
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

    private SupportMapFragment fragment;
    private GoogleMap map;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        map.getUiSettings().setAllGesturesEnabled(true);


        final List<Snowman> snowmen = SnowmanHelper.getAll(getContext());

        int size = snowmen.size();
        for (int i = 0; i < size; i++){
            Snowman snowman = snowmen.get(i);

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(snowman.getLatitude(), snowman.getLongitude()))
                    .title(snowman.getName()));

            marker.setSnippet(String.valueOf(i));

        }

        map.setInfoWindowAdapter(new SnowmanInfoWindowAdapter(getContext(), snowmen));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Snowman snowman = snowmen.get(Integer.parseInt(marker.getSnippet()));
                snowman.setFavorite(!snowman.isFavorite());

                SnowmanHelper.setFavoriteSnowman(getContext(), snowman);

                marker.showInfoWindow();

            }
        });
    }
}
