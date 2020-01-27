package com.ybbbi.qqdemo.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.presenter.Interface.ConversationPresenter;
import com.ybbbi.qqdemo.view.Interface.ConversatrionIView;
import com.ybbbi.qqdemo.view.activity.ChatActivity;
import com.ybbbi.qqdemo.view.adapter.ConversationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * ybbbi
 * 2020-01-11 23:03
 */
public class MessageFragment extends BaseFragment implements ConversatrionIView {

    private RecyclerView recyclerview;
    private FloatingActionButton fab;
    private ConversationAdapter adapter;
    private ConversationPresenter presenter;

    @Override
    protected void initView() {
        recyclerview = view.findViewById(R.id.recyclerview);
        fab = view.findViewById(R.id.fab);
        adapter = new ConversationAdapter(null);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        presenter=new ConversationPresenter(this);
        presenter.getConversations();
        adapter.setAddItemClickListener(username -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("contact",username);
            startActivity(intent);
        });
    }

    @Override
    public int bindView() {
        return R.layout.conversation;
    }

    @Override
    public void getConversations(List<EMConversation> list) {
        adapter.setConversations(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessageEvent(List<EMMessage> list){
        presenter.getConversations();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getConversations();
    }
}
