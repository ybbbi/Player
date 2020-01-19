package com.ybbbi.qqdemo.view.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.MeasureUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.view.adapter.FragmentViewpagerAdapter;
import com.ybbbi.qqdemo.view.fragment.ContactFragment;
import com.ybbbi.qqdemo.view.fragment.DongTaiFragment;
import com.ybbbi.qqdemo.view.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager fragment_viewpager;
    private List<Fragment> list;
    private Toolbar toolbar;
    private TextView title;
    private long currentTime;
    private DrawerLayout drawerlayout;
    private LinearLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        initfragment();
        init();
        initactionbar();


    }

    private void initactionbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, 0, 0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
        setStatusBarColor(this, R.color.colorPrimary);
    }

    /**
     * 菜单栏创建
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuBuilder builder = (MenuBuilder) menu;

        builder.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addfriends:
//                ToastUtils.ShowMsg(getString(R.string.addfriends), this);
                startActivity(new Intent(this, AddFriends.class));
                break;
            case R.id.about:
                ToastUtils.ShowMsg(getString(R.string.about), this);
                break;
            case R.id.scan:
                ToastUtils.ShowMsg(getString(R.string.scan), this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 菜单栏的点击事件
     */


    private void initfragment() {
        MessageFragment baseFragment1 = new MessageFragment();
        ContactFragment baseFragment2 = new ContactFragment();
        DongTaiFragment baseFragment3 = new DongTaiFragment();
        list = new ArrayList();
        list.add(baseFragment1);
        list.add(baseFragment2);
        list.add(baseFragment3);


    }

    private void init() {


        initDrawer();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.text_title);

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
                switchFragment(bottomNavigationView.getMenu().getItem(position).getItemId());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {


            switchFragment(item.getItemId());

            return true;

        });
    }

    private void initDrawer() {
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer = (LinearLayout) findViewById(R.id.drawer);
        Point point = MeasureUtils.init(this).getScreenWH();
        drawer.getLayoutParams().width = point.x/4*3;
        drawer.setLayoutParams(drawer.getLayoutParams());
    }

    /**
     * 切换fragment
     */
    private void switchFragment(int id) {
        switch (id) {
            case R.id.item_tab1:
                fragment_viewpager.setCurrentItem(0);
                title.setText(R.string.message);
                break;
            case R.id.item_tab2:
                title.setText(R.string.contact);
                fragment_viewpager.setCurrentItem(1);
                break;
            case R.id.item_tab3:
                title.setText(R.string.dongtai);
                fragment_viewpager.setCurrentItem(2);
                break;
        }
    }

    long last = 0;

    @Override
    public void onBackPressed() {
        ToastUtils.ShowMsg("再次点击返回键退出应用程序", this);
        currentTime = System.currentTimeMillis();

        if (currentTime - last > 2000) {
            last = System.currentTimeMillis();
            return;
        } else {
            finish();
        }

        super.onBackPressed();
    }
}
