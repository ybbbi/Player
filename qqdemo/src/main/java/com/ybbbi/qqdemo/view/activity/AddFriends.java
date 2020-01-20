package com.ybbbi.qqdemo.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.model.User;
import com.ybbbi.qqdemo.presenter.AddFriensPresenter;
import com.ybbbi.qqdemo.view.Interface.AddFriendsIView;
import com.ybbbi.qqdemo.view.adapter.AddFriendsAdapter;

import java.util.List;

/**
 * ybbbi
 * 2020-01-19 19:27
 */
public class AddFriends extends BaseActivity implements View.OnClickListener, AddFriendsIView {

    private Toolbar toolbar;
    private ImageView nodata_img;
    private ImageButton back;
    private RecyclerView recyclerview;
    private SearchView searchView;
    private AddFriendsAdapter adapter;
    private AddFriensPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        setStatusBarColor(this, R.color.colorPrimary);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        recyclerview = (RecyclerView) findViewById(R.id.addfriends_recyclerview);
        presenter = new AddFriensPresenter(this);

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchFriend(query);
                setAdapter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchFriend(newText);
                setAdapter();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new AddFriendsAdapter(null, null);
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(AddFriends.this));
        recyclerview.setAdapter(adapter);
        adapter.setAddFriensClickListener(username ->
                presenter.addFriend(username)
        );
    }

    private void inittoolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("");

    }

    //返回键
    @Override
    public void onClick(View view) {
        finish();
    }

    /**
     * 查询的结果
     */
    @Override
    public void hasFriends(List<User> userlist, List<String> friendsList, boolean isSuccess, String message) {
        if (isSuccess) {
            nodata_img.setVisibility(View.GONE);
            adapter.setFriendArrayList(friendsList);
            adapter.setUserArrayList(userlist);
            adapter.notifyDataSetChanged();
            //设置添加好友监听


        } else {
            Snackbar.make(recyclerview, message, 2000).show();
            nodata_img.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否添加成功
     * @param isSuccess
     * @param message
     */
    @Override
    public void isAddSuccessful(boolean isSuccess, String message) {
        if (isSuccess){
            Snackbar.make(recyclerview,message,2000)
                    .setText(message)
                    .setTextColor(getResources().getColor(R.color.colorPrimary))
                    .setAction(getString(R.string.sure),view -> {

                    })
                    .show();


        }else{
            Snackbar.make(recyclerview,message,2000).show();
        }
    }
}
