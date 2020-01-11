package com.ybbbi.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ybbbi.player.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_iv_bg)
    ImageView splashIvBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // 播放缩放动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            /**
                动画开始
             */
            public void onAnimationStart(Animation animation) {

            }

            @Override
            /**
                动画结束
             */
            public void onAnimationEnd(Animation animation) {
                startMainActivity();
            }

            @Override

            /**
                动画重复播放
             */
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashIvBg.startAnimation(animation);
    }

    private void startMainActivity() {
        // 打开主界面
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        // 关闭当前界面
        finish();

        // 动态设置界面转场效果
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
