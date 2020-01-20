package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ybbbi.qqdemo.Utils.DbUtils;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.model.User;
import com.ybbbi.qqdemo.presenter.Interface.AddFriendsIPresenter;
import com.ybbbi.qqdemo.view.Interface.AddFriendsIView;

import java.util.List;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * ybbbi
 * 2020-01-20 11:09
 */
public class AddFriensPresenter implements AddFriendsIPresenter {
    AddFriendsIView view;

    public AddFriensPresenter(AddFriendsIView view) {
        this.view = view;
    }

    @Override
    public void searchFriend(String keyword) {
        String currentUser = EMClient.getInstance().getCurrentUser();
        //模糊查询根据首字母查询，排除当前用户
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereStartsWith("username", keyword)
                .addWhereNotEqualTo("username", currentUser)
                .findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> userlist, BmobException e) {
                        if (e == null && userlist != null) {
                            //成功有数据,查询本地数据库好友列表
                            List<String> friendsList = DbUtils.initContact(currentUser);
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.hasFriends(userlist, friendsList, true, null);

                                }
                            });
                        } else {
                            if (e == null) {
                                //成功,但是无数据
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.hasFriends(null, null, false, "没有满足条件的用户");

                                    }
                                });
                            }
                            //失败
                            ThreadUtils.runOnMainThread(() ->
                                    view.hasFriends(null, null, false, e.getMessage())
                            );
                        }
                    }
                });
    }

    @Override
    public void addFriend(String user) {
        ThreadUtils.runOnChildThread(() -> {
            try {
                EMClient.getInstance().contactManager().addContact(user, "申请添加好友");
                ThreadUtils.runOnMainThread(() -> view.isAddSuccessful(true,"添加成功"));
            } catch (HyphenateException e) {
                e.printStackTrace();
                ThreadUtils.runOnMainThread(() -> view.isAddSuccessful(false,e.getMessage()));

            }
        });
    }
}
