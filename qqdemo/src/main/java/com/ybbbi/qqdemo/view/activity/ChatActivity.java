package com.ybbbi.qqdemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMMessage;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ActivityManager;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.presenter.ChatPresenter;
import com.ybbbi.qqdemo.view.Interface.ChatIView;
import com.ybbbi.qqdemo.view.adapter.ChatAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ChatActivity extends BaseActivity implements View.OnClickListener, ChatIView {

    private Toolbar toolbar;
    private TextView tv_title;
    private RecyclerView recyclerview;
    private EditText et_message;
    private Button btn_send;
    private ImageButton back;
    private ChatPresenter presenter;
    private ChatAdapter chatAdapter;
    private String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setStatusBarColor(this, R.color.btn_normal);
        presenter=new ChatPresenter(this);
        init();
        ActivityManager.getInstance().getList().add(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(null);
        recyclerview.setAdapter(chatAdapter);
        presenter.getMessageFromUser(contact);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().getList().remove(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {

        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
   public  void onGetMessageEvent(List<EMMessage> list){
        presenter.getMessageFromUser(contact);
    }

    private void init() {
        Intent intent = getIntent();
        contact = intent.getStringExtra("contact");

        toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(contact);
        recyclerview = (RecyclerView) findViewById(R.id.rv_chat);

        et_message = (EditText) findViewById(R.id.et_message);
        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isNULL(charSequence.toString())) {
                    //无内容，不可发送
                    btn_send.setEnabled(false);
                    btn_send.setBackgroundResource(R.drawable.addfriend_btn_pressed);
                    btn_send.setTextColor(getResources().getColor(R.color.texthit));
                } else {
                    //有内容 ，可发送
                    btn_send.setEnabled(true);
                    btn_send.setTextColor(getResources().getColor(R.color.white));
                    btn_send.setBackgroundResource(R.drawable.addfriend_btn_normal);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_send = (Button) findViewById(R.id.btn_send);
        back = (ImageButton) findViewById(R.id.back);

        btn_send.setOnClickListener(this);
        back.setOnClickListener(this);
        inittoolbar();

    }

    private void inittoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_send:
                String message = et_message.getText().toString().trim();
                presenter.sendMessage(message,contact);
                et_message.setText("");
//                ToastUtils.ShowMsg(message, this);
             /*   //隐藏输入法 第一个view 的token，第二个flag
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(recyclerview.getWindowToken(),0);*/

                break;
        }
    }

    @Override
    public void getHistory(List<EMMessage> emMessages) {
        chatAdapter.setMessages(emMessages);
        chatAdapter.notifyDataSetChanged();
        if(chatAdapter.getItemCount()>0){
            recyclerview.smoothScrollToPosition(chatAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateList() {
        chatAdapter.notifyDataSetChanged();
        if(chatAdapter.getItemCount()>0){
            recyclerview.smoothScrollToPosition(chatAdapter.getItemCount()-1);
        }
    }
}
