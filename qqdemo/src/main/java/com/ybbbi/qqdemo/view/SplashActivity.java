package com.ybbbi.qqdemo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ybbbi.qqdemo.MainActivity;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.presenter.SplashPresenter;
import com.ybbbi.qqdemo.view.Interface.SplashIView;

public class SplashActivity extends AppCompatActivity implements SplashIView {
    //进入秒数
    private int second = 2;
    private boolean islogin = true;
    private static final int DELAYMESSAGE = 0;

    private Handler handlerEnter = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            second--;
            switch (msg.what) {
                case DELAYMESSAGE:
                    if (second == 0) {
                        enterActivity(islogin);
                    } else {
                        handlerEnter.sendEmptyMessageDelayed(DELAYMESSAGE, 1000);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //4s秒后进入主界面
        splashPresenter = new SplashPresenter(this);
        splashPresenter.checkLogin();

    }

    @Override
    protected void onPause() {
        handlerEnter.removeMessages(DELAYMESSAGE);
        super.onPause();
    }

    @Override
    protected void onResume() {
        handlerEnter.sendEmptyMessageDelayed(DELAYMESSAGE,1000);
        super.onResume();
    }

    private void enterMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void enterLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    //设置标识，根据返回的标识跳转
    @Override
    public void isLogined(boolean islogin) {
        this.islogin = islogin;
        handlerEnter.sendEmptyMessageDelayed(DELAYMESSAGE, 1000);

    }

    private void enterActivity(boolean islogin) {
        if (islogin) {
            enterMainActivity();
        } else {
            enterLoginActivity();
        }
    }
}
