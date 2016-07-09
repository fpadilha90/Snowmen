package com.fpadilha.snowmen.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.FavsListAdapter;
import com.fpadilha.snowmen.adapters.SnowmenListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by felipe on 08/07/2016.
 */
@EFragment(R.layout.list_fragment_favs)
public class FavoriteSnowmenListFragment extends Fragment implements OnThread{

    @ViewById
    ListView list;
    @Bean
    FavsListAdapter adapter;

    @AfterViews
    void afterViews(){
        bindAdapter();

    }

    @Override
    public void onThread(boolean onThread) {
        if (!onThread) {
            bindAdapter();
        }
    }

    @UiThread
    void bindAdapter() {
        if (isAdded()) {
            list.setAdapter(adapter);
            adapter.bind();
        }
    }
}
