package com.ybbbi.qqdemo.view.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ActivityManager;
import com.ybbbi.qqdemo.Utils.MeasureUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity {

    private ProgressDialog progressDialog;

private View decorView;
    private Point point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_base);
        /*point = MeasureUtils.init(BaseActivity.this).getScreenWH();

        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {


            private int prex;

            @Override
            public void onDragStateChange(int state) {
                Log.e("ybbbi", "onDragStateChange: " + state);
                switch (state) {
                    //0为返回最初点，不退出； 1为按下； 2为抬起； 3为画到最右侧，结束；
                    case 2:
                        //获取抬手位置
                        if (prex < (point.x / 3.789)) {
                            ValueAnimator animatorin = ValueAnimator.ofInt(prex, 0);
                            animatorin.addUpdateListener(valueAnimator -> {
                                int value = (int) valueAnimator.getAnimatedValue();
                                decorView.scrollTo(value,0);

                            });

                            animatorin.setDuration(200);
                            animatorin.start();
                        } else {
                            //回到最左边
                            ValueAnimator animatorout = ValueAnimator.ofInt(prex, -point.x);
                            animatorout.addUpdateListener(valueAnimator -> {
                                int value = (int) valueAnimator.getAnimatedValue();
                                decorView.scrollTo(value,0);

                            });

                            animatorout.setDuration(200);
                            animatorout.start();

                        }
                        break;
                }
            }

            @Override
            public void onEdgeTouch(int oritentationEdgeFlag) {

            }

            @Override
            public void onDragScrolled(float scrollPercent) {
                Log.e("ybbbi", "scrollPercent: " + scrollPercent);



                LinkedList<Activity> list = ActivityManager.getInstance().getList();
                prex = (int) ((1 - scrollPercent) / 2 * point.x);
                Log.e("ybbbi", "prex: " + prex);

                decorView = list.get(list.size() - 2).getWindow().getDecorView();
               decorView .scrollTo(prex, 0);

            }
        });
*/


    }


    protected void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                boolean res = hideKeyboard(this, v.getWindowToken());
                if (res) {
                    //隐藏了输入法，则不再分发事件
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {

                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，
        // 第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param context
     * @param token
     */
    public static boolean hideKeyboard(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    /**
     * 展示等待窗口
     */
    protected void showLoading(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    /**
     * 隐藏等待窗口
     */
    protected void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissLoading();
        super.onDestroy();
    }
}
