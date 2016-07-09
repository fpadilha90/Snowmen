package com.fpadilha.snowmen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.activities.MainActivity;
import com.fpadilha.snowmen.fragments.FavoriteSnowmenListFragment_;
import com.fpadilha.snowmen.fragments.MapsFragment_;
import com.fpadilha.snowmen.fragments.OnThread;
import com.fpadilha.snowmen.fragments.SnowmenListFragment_;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[];
    private SparseArray<OnThread> map;
    private MainActivity activity;

    public MainFragmentPagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.map = new SparseArray<>();
        this.activity = activity;
        tabTitles = new String[]{activity.getString(R.string.title_fragment_list),
                activity.getString(R.string.title_fragment_maps),
                activity.getString(R.string.title_fragment_favs)};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = SnowmenListFragment_.builder().build();
        }  else if (position == 1) {
            fragment = MapsFragment_.builder().build();
        } else {
            fragment = FavoriteSnowmenListFragment_.builder().build();
        }
        map.put(position, (OnThread) fragment);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public void refreshFragments(int tab) {
        for (int i = 0; i < map.size(); i++) {
            int key = map.keyAt(i);
            OnThread fragment = map.get(key);
            if (((Fragment) fragment).isAdded() && i != tab) fragment.onThread(activity.onThread);
        }
    }
    public void refreshFragments() {
        for (int i = 0; i < map.size(); i++) {
            int key = map.keyAt(i);
            OnThread fragment = map.get(key);
            if (((Fragment) fragment).isAdded()) fragment.onThread(activity.onThread);
        }
    }
}