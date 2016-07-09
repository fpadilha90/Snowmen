package com.fpadilha.snowmen.activities;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.MainFragmentPagerAdapter;
import com.fpadilha.snowmen.services.AppRefreshTask;
import com.fpadilha.snowmen.services.TaskCallBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements TaskCallBack {

    private MainFragmentPagerAdapter adapter;

    @ViewById Toolbar toolbar;
    @ViewById ViewPager viewPager;
    @ViewById TabLayout tabs;
    @ViewById ProgressBar progressBar;

    @NonConfigurationInstance public boolean onThread;

    @NonConfigurationInstance
    @Bean AppRefreshTask appRefreshTask;

    @AfterViews void afterViews(){
        setSupportActionBar(toolbar);
        appRefresh();
    }

    @UiThread void fillAdapter() {
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void appRefresh() {
        if (!onThread) {

            setOnThread(true);
            appRefreshTask.start(this);
        }
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
        Snackbar.make(tabs, error, Snackbar.LENGTH_LONG).show();
    }
}
