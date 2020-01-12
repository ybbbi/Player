package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.model.User;
import com.ybbbi.qqdemo.presenter.Interface.RegisterIPresenter;
import com.ybbbi.qqdemo.view.Interface.RegisterIView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterPresenter implements RegisterIPresenter {
    private RegisterIView view;

    public RegisterPresenter(RegisterIView view) {
        this.view = view;
    }

    @Override
    public void OnRegister(String username, String pwd) {
        //设置bean类，orm 对象关系映射
        final User user = new User();
        user.setPwd(pwd);
        user.setUsername(username);

        //将数据传给bmob云服务
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //bmob成功,将数据保存到环信
                    ThreadUtils.runOnChildThread(() -> {

                        try {
                            EMClient.getInstance().createAccount(username, pwd);
                            //环信成功，在主线程更新
                            ThreadUtils.runOnMainThread(() ->
                                view.Success(s,username,pwd)

                            );
                        } catch (HyphenateException he) {
                            ThreadUtils.runOnMainThread(() ->
                                user.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                        view.Fail("用户名已经注册");
                                    }
                                })


                            );
                            he.printStackTrace();
                        }

                    });
                } else {
                    //失败
                    view.Fail(e.toString());
                }
            }
        });


    }
}
