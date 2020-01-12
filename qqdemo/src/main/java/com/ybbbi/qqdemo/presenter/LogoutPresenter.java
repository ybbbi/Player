package com.ybbbi.qqdemo.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.callback.MyEmCallBack;
import com.ybbbi.qqdemo.presenter.Interface.LogoutIPresenter;
import com.ybbbi.qqdemo.view.Interface.LogoutIView;

/**
 * ybbbi
 * 2020-01-12 13:19
 */
public class LogoutPresenter implements LogoutIPresenter {
    LogoutIView view;

    public LogoutPresenter(LogoutIView view) {
        this.view = view;
    }

    @Override
    public void Logout() {
        EMClient.getInstance().logout(true, new MyEmCallBack() {
            @Override
            public void success() {
                view.onLogout(true,"");
            }

            @Override
            public void error(int i, String s) {
                view.onLogout(false,s);
            }
        });
    }
}
