package com.ybbbi.qqdemo.callback;

import com.hyphenate.EMCallBack;
import com.ybbbi.qqdemo.Utils.ThreadUtils;

/**
 * ybbbi
 * 2020-01-12 13:23
 * 封装回调，将更新ui操作在主线程中
 */
public abstract class MyEmCallBack implements EMCallBack {
    public abstract void success();
    public abstract void error(int i, String s);
    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(()->
        success());
    }

    @Override
    public void onError(int i, String s) {
        ThreadUtils.runOnMainThread(()->
                error(i,s));
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
