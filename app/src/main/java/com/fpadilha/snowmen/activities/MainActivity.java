package com.fpadilha.snowmen.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapter.MainFragmentPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private MainFragmentPagerAdapter adapter;

    @ViewById Toolbar toolbar;
    @ViewById ViewPager viewPager;
    @ViewById TabLayout tabs;

    @AfterViews void afterViews(){
        setSupportActionBar(toolbar);
        fillAdapter();

    }

    private void fillAdapter() {
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

}
