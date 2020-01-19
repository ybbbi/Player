package com.ybbbi.qqdemo.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
public class AddFriends extends BaseActivity {

    private Toolbar toolbar;
    private TextView title;
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
        title = (TextView) findViewById(R.id.text_title);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        inittoolbar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_friends, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        searchView = (SearchView) item.getActionView();

        searchView.setQueryHint("搜索好友");
        searchView.setOnSearchClickListener(view -> title.setText(""));
        searchView.setOnCloseListener(() -> {
            title.setText(R.string.search);
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void inittoolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
