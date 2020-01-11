package com.ybbbi.player.utils;

import android.content.Context;
import android.widget.Toast;

import com.ybbbi.player.Global.MyApp;

public class ToastUtils {
    /**
     * 获取实例
     *
     * @return
     */
    private static ToastUtils toastUtils;

    private static Toast toast;


    public static void show(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.mContext, text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }
}
