package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.ybbbi.qqdemo.presenter.Interface.ChatIPresenter;

import java.util.List;

/**
 * ybbbi
 * 2020-01-20 15:43
 */
public class ChatPresenter implements ChatIPresenter {
    @Override
    public List<EMMessage> getMessageFromUser(String user) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user);
        //获取所有聊天记录
        conversation.getAllMessages();
        //从数据库中获取聊天记录，第一个参数为消息记录的id， 第二个参数为前几条+1,也就是21条数据
        EMMessage lastMessage = conversation.getLastMessage();

        List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), 20);
        return emMessages;

    }
}
