package com.ybbbi.qqdemo.view.Interface;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * ybbbi
 * 2020-01-27 16:40
 */
public interface ChatIView {
    void getHistory(List<EMMessage> emMessages);

    void updateList();
}
