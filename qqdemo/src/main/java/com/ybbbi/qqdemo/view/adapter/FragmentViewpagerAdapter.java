package com.ybbbi.qqdemo.view.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ybbbi
 * 2020-01-11 22:45
 */
public class FragmentViewpagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private int behavior;


    private List<Fragment> list;
    public FragmentViewpagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> list) {
        super(fm, behavior);
        this.fm = fm;
        this.behavior = behavior;
        this.list = list;
    }







    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
