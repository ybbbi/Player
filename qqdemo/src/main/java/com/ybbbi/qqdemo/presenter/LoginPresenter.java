package com.ybbbi.qqdemo.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.presenter.Interface.LoginIPresenter;
import com.ybbbi.qqdemo.view.Interface.LoginIView;

public class LoginPresenter implements LoginIPresenter {
    public LoginPresenter(LoginIView view) {
        this.view = view;
    }

    private boolean isSuccess = false;
    private LoginIView view;

    @Override
    public void Login(String username, String passwd) {
        EMClient.getInstance().login(username, passwd, new EMCallBack() {
            @Override
            public void onSuccess() {

                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        isSuccess=true;
                        view.getLoginState(username, isSuccess, null);

                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                isSuccess=false;
                view.getLoginState(username, isSuccess, s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
