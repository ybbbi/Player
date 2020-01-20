package com.ybbbi.qqdemo.presenter.Interface;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * ybbbi
 * 2020-01-20 15:42
 */
public interface ChatIPresenter {
//    void sendMessage(String user,String msg);
    List<EMMessage> getMessageFromUser(String user);
}
