package com.ybbbi.qqdemo.presenter;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.callback.MyEmCallBack;
import com.ybbbi.qqdemo.presenter.Interface.ChatIPresenter;
import com.ybbbi.qqdemo.view.Interface.ChatIView;

import java.util.List;

/**
 * ybbbi
 * 2020-01-20 15:43
 */
public class ChatPresenter implements ChatIPresenter {
    private ChatIView view;

    public ChatPresenter(ChatIView view) {
        this.view = view;
    }

    @Override
    public List<EMMessage> getMessageFromUser(String user) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user);
        //获取所有聊天记录
        //从数据库中获取聊天记录，第一个参数为消息记录的id， 第二个参数为前几条+1,也就是21条数据
        if (conversation != null) {
            //设置所有消息为已读
            conversation.markAllMessagesAsRead();
            EMMessage lastMessage = conversation.getLastMessage();

            List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), conversation.getAllMsgCount());
            emMessages.add(lastMessage);
            view.getHistory(emMessages);
        }
        return null;

    }

    @Override
    public void sendMessage(String message, String contact) {

        EMMessage sendMessage = EMMessage.createTxtSendMessage(message, contact);
        ThreadUtils.runOnChildThread(() -> {
        EMClient.getInstance().chatManager().sendMessage(sendMessage);

        });
        EMClient.getInstance().chatManager().saveMessage(sendMessage);
        getMessageFromUser(contact);
        sendMessage.setMessageStatusCallback(new MyEmCallBack() {
            @Override
            public void success() {
                view.updateList();
            }

            @Override
            public void error(int i, String s) {
                view.updateList();
            }
        });
    }
}
