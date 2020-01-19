package com.ybbbi.qqdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * ybbbi
 * 2020-01-19 22:31
 */
public class MeasureUtils {
    private static Activity mactivity;

    private MeasureUtils() {
    }
    private static MeasureUtils measureUtils;
    public static MeasureUtils init(Activity activity) {
        if(measureUtils==null){
            measureUtils=new MeasureUtils();
        }
        mactivity=activity;
        return measureUtils;
    }

    /**
     * 获取屏幕宽高
     */
    public  Point getScreenWH() {
        Point point = new Point();
        if (mactivity != null) {

            DisplayMetrics metrics=new DisplayMetrics();
            mactivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            point.x=metrics.widthPixels;
            point.y=metrics.heightPixels;
            return point;
        } else {
            throw new RuntimeException("请调用init初始化");
        }
    }
}

