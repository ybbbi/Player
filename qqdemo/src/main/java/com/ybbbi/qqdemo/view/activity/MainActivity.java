package com.ybbbi.qqdemo.view.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.view.activity.BaseActivity;
import com.ybbbi.qqdemo.view.adapter.FragmentViewpagerAdapter;
import com.ybbbi.qqdemo.view.fragment.BaseFragment;
import com.ybbbi.qqdemo.view.fragment.ContactFragment;
import com.ybbbi.qqdemo.view.fragment.DongTaiFragment;
import com.ybbbi.qqdemo.view.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager fragment_viewpager;
    private List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        initactionbar();
        initfragment();
        init();


    }

    private void initactionbar() {
        setStatusBarColor(this,R.color.colorPrimary);
    }

    private void initfragment() {
        MessageFragment baseFragment1 = new MessageFragment();
        ContactFragment baseFragment2 = new ContactFragment();
        DongTaiFragment baseFragment3 = new DongTaiFragment();
        list=new ArrayList();
        list.add(baseFragment1);
        list.add(baseFragment2);
        list.add(baseFragment3);


    }

    private void init() {

        fragment_viewpager = (ViewPager) findViewById(R.id.fragment_viewpager);
        fragment_viewpager.setAdapter(new FragmentViewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //使用自己选中图片的背景
        bottomNavigationView.setItemIconTintList(null);
        //将viewpager与bottomnavigationview绑定
        fragment_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switchFragment(item.getItemId());

                return true;
            }
        });
    }

    /**
     * 切换fragment
     */
    private void switchFragment(int id) {
        switch (id) {
            case R.id.item_tab1:
                fragment_viewpager.setCurrentItem(0);
                break;
            case R.id.item_tab2:
                fragment_viewpager.setCurrentItem(1);
                break;
            case R.id.item_tab3:
                fragment_viewpager.setCurrentItem(2);
                break;
        }
    }
}
