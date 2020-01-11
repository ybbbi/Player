package com.ybbbi.player.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ybbbi
 * 2019-12-07 00:20
 */
public class MvviewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;

    /**
     *
     * @param fm
     * @param behavior 预加载模式
     * @param list
     */
    public MvviewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> list) {
        super(fm, behavior);
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
