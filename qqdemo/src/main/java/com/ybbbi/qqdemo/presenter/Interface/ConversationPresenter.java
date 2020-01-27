package com.ybbbi.qqdemo.presenter.Interface;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.ybbbi.qqdemo.view.Interface.ConversatrionIView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ybbbi
 * 2020-01-27 20:07
 */
public class ConversationPresenter implements ConversatioinIPresenter {
    private ConversatrionIView view;

    public ConversationPresenter(ConversatrionIView view) {
        this.view = view;
    }

    @Override
    public void getConversations() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Collection<EMConversation> values = conversations.values();
        //获取回话的集合
        List<EMConversation> list=new ArrayList<>(values);
        // 返回值为int类型，大于0表示正序，小于0表示逆序
        Collections.sort(list, (EMConversation t, EMConversation t1)->
                 (int) (t.getLastMessage().getMsgTime()-t1.getLastMessage().getMsgTime())

        );
        view.getConversations(list);
    }
}
