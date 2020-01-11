package com.ybbbi.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ybbbi.player.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {
    @Bind(R.id.player_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playeractivity);
        ButterKnife.bind(this);
//        JzvdStd player = findViewById(R.id.player);


        //toolbar设置
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeButtonEnabled(true);

        TextView text = findViewById(R.id.playeractivity_text);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        String imgurl = intent.getStringExtra("imgurl");
        text.setText("url:"+url+"\n"+"title:"+title+"\n"+"imgurl:"+imgurl);
        getSupportActionBar().setTitle(title);

//        player.setUp(url,title);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case   android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
