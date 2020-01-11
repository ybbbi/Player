package com.ybbbi.qqdemo.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    public static void ShowMsg(String msg,Context context){
        if(toast==null){

            toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
