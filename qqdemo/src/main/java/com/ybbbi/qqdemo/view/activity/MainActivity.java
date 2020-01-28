package com.ybbbi.qqdemo.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ActivityManager;
import com.ybbbi.qqdemo.Utils.MeasureUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.view.adapter.FragmentViewpagerAdapter;
import com.ybbbi.qqdemo.view.fragment.ContactFragment;
import com.ybbbi.qqdemo.view.fragment.DongTaiFragment;
import com.ybbbi.qqdemo.view.fragment.MessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private BottomNavigationItemView child;
    private BottomNavigationMenuView menuView;
    private View view;
    private TextView tv_unread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().getList().add(this);

        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        initfragment();
        init();

        initactionbar();


    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().getList().remove(this);
        super.onDestroy();
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

    @SuppressLint("RestrictedApi")
    private void init() {


        initDrawer();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.text_title);

        fragment_viewpager = (ViewPager) findViewById(R.id.fragment_viewpager);
        fragment_viewpager.setAdapter(new FragmentViewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //使用自己选中图片的背景
        bottomNavigationView.setItemIconTintList(null);
        //初始化角标控件
        initUpdateUnreadView();

        updateBadgeDrawable();


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

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBadgeDrawable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(List<EMMessage> list){
        updateBadgeDrawable();
    }


    //更新图标数量
    public void updateBadgeDrawable() {


        int unreadMessageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
        if (unreadMessageCount == 0) {
            tv_unread.setVisibility(View.GONE);
        } else if (unreadMessageCount > 99) {
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(String.valueOf(99));
        }else{
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(String.valueOf(unreadMessageCount));
        }
    }

    private void initUpdateUnreadView() {
        menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        child = (BottomNavigationItemView) menuView.getChildAt(0);
        view = LayoutInflater.from(this).inflate(R.layout.updatebadge, child, false);
        child.addView(view);
        tv_unread = child.findViewById(R.id.tv_unread);
    }

    private void initDrawer() {
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer = (LinearLayout) findViewById(R.id.drawer);
        Point point = MeasureUtils.init(this).getScreenWH();
        drawer.getLayoutParams().width = point.x / 4 * 3;
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
    public void onBackPressedSupport() {
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
