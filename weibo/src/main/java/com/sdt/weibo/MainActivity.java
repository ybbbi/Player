package com.sdt.weibo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private SmartRefreshLayout refresh;
    private TextView textview;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    //结束刷新
            refresh(refresh);
                    textview.layout(0,0,width,height);

                    break;
                case 1:
                    //开始控件隐藏动画
                    animate();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        refresh = findViewById(R.id.smartrefreshlayout);
        textview = findViewById(R.id.textview);
        textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                } else {
                    textview.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                }
                height = textview.getHeight();
                width = textview.getWidth();
            }
        });


        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //耗时操作
                handler.sendEmptyMessageDelayed(0,3000);


            }
        });


    }

    private void refresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        handler.sendEmptyMessageDelayed(1,3000);

    }

    int height = 0;
    int width = 0;

    private void animate() {


        if (height != 0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, -height);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                private int value;

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    value = (int) valueAnimator.getAnimatedValue();
                    textview.layout(0, value, width, value + height);


                }
            });
            animator.setDuration(1000);
            animator.start();

        }
    }


}
