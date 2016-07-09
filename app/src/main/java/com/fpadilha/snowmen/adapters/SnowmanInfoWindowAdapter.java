package com.fpadilha.snowmen.adapters;

import android.content.Context;
import android.view.View;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.models.Snowman;
import com.fpadilha.snowmen.views.SnowmanView;
import com.fpadilha.snowmen.views.SnowmanView_;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by felipe on 09/07/2016.
 */
public class SnowmanInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final Context context;
    private final List<Snowman>  snowmen;

//    private final View myContentsView;

    public SnowmanInfoWindowAdapter(Context context, List<Snowman> snowmen){
        this.context = context;
        this.snowmen = snowmen;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        final SnowmanView snowmanView;

        Snowman snowman = snowmen.get(Integer.parseInt(marker.getSnippet()));

        snowmanView = SnowmanView_.build(context);

        snowmanView.bind(snowman);

        return snowmanView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
