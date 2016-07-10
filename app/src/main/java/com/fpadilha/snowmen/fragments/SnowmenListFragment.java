package com.fpadilha.snowmen.fragments;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.SnowmenListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by felipe on 08/07/2016.
 */
@EFragment(R.layout.list_fragment_snowmen)
public class SnowmenListFragment extends Fragment implements OnThread {

    @ViewById
    ListView list;
    @ViewById
    TextView message;
    @Bean
    SnowmenListAdapter adapter;

    @AfterViews
    void afterViews() {
        bindAdapter();

    }

    @Override
    public void onThread(boolean onThread) {
        Log.e("Favorite OnThread", String.valueOf(onThread));
        if (!onThread) {
            bindAdapter();
        }
    }

    @UiThread
    void bindAdapter() {
        list.setAdapter(adapter);
        adapter.bind();

        if (isAdded()) {
            if (adapter.getCount() == 0) {
                message.setText(getString(R.string.without_snowmen));
                message.setVisibility(View.VISIBLE);
            } else {
                message.setText("");
                message.setVisibility(View.GONE);
            }
        }
    }
}
