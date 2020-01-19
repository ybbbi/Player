package com.ybbbi.qqdemo.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.ybbbi.qqdemo.R;

/**
 * ybbbi
 * 2020-01-19 19:27
 */
public class AddFriends extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageButton back;
    private RecyclerView recyclerview;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        setStatusBarColor(this, R.color.colorPrimary);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        inittoolbar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_friends, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("搜索好友");
        searchView.setIconifiedByDefault(false);
        searchView.setBackgroundResource(R.drawable.search);

        searchView.setFocusable(false);

        return super.onCreateOptionsMenu(menu);
    }

    private void inittoolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("");

    }


    @Override
    public void onClick(View view) {
        finish();
    }
}
