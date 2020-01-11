package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMClient;
import com.ybbbi.qqdemo.presenter.Interface.SplashIPresenter;
import com.ybbbi.qqdemo.view.Interface.SplashIView;

public class SplashPresenter implements SplashIPresenter {
    private SplashIView view;

    public SplashPresenter(SplashIView view) {
        this.view = view;
    }

    /**
     * 判断是否登陆过
     */
    @Override
    public void checkLogin() {
        if(EMClient.getInstance().isLoggedInBefore()&&EMClient.getInstance().isConnected()){
            //判断是否登陆过并且是否与服务器建立连接
            view.isLogined(true);
        }else {
            //
            view.isLogined(false);

        }
    }
}
