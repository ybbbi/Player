package com.ybbbi.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.ybbbi.player.R;
import com.ybbbi.player.fragment.TestFragment;
import com.ybbbi.player.fragment.mvpage.MvFragment;
import com.ybbbi.player.fragment.yuedanpage.YuedanFragment;
import com.ybbbi.player.fragment.homepage.HomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;
    private SparseArray<Fragment> sparseArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 将 ToolBar 设置为标题栏
        setSupportActionBar(toolbar);
        // 修改 ToolBar 属性
        getSupportActionBar().setTitle("VMPlayer");

        // 初始化 Fragment 集合
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.bottombar_home,new HomeFragment());
        sparseArray.append(R.id.bottombar_mv, new MvFragment());
        sparseArray.append(R.id.bottombar_vbang,TestFragment.newInstance("V榜"));
        sparseArray.append(R.id.bottombar_yuedan,new YuedanFragment());

        // 处理底部栏

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                // 切换到当前按钮对应的 Fragment
                Fragment fragment = sparseArray.get(tabId);
                switchFragment(fragment);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 创建 menu 菜单，这个菜单会依附到 ToolBar 上
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理 menu 菜单的点击监听
        switch (item.getItemId()){
            case R.id.menu_main_settings:
//                Toast.makeText(this, "跳转到设置界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    /**
     * 将参数里的 Fragment 显示出来
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container,fragment);


        transaction.commit();
    }
        long exitTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-exitTime>2000){
            Toast.makeText(this,"再次按退出将退出程序",Toast.LENGTH_LONG).show();
            exitTime=System.currentTimeMillis();
            return;
        }else{
            finish();
        }
        super.onBackPressed();
    }
}
