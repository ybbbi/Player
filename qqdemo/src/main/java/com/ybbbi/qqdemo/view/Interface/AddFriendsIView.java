package com.ybbbi.qqdemo.view.Interface;

import com.ybbbi.qqdemo.model.User;

import java.util.List;

/**
 * ybbbi
 * 2020-01-20 11:28
 */
public interface AddFriendsIView {
    void hasFriends(List<User> list, List<String> friendsList, boolean isSuccess, String message);

    void isAddSuccessful(boolean b, String message);
}
