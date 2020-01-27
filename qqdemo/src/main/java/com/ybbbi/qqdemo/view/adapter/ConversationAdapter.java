package com.ybbbi.qqdemo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ybbbi
 * 2020-01-20 10:13
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private int index;

    private List<EMConversation> conversations = new ArrayList<>();

    private User user;


    public void setConversations(List<EMConversation> conversations) {
        if (conversations != null) {

            this.conversations = conversations;
        }
    }

    public ConversationAdapter(List<EMConversation> conversations) {

        this.conversations = conversations;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_conversation, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //头像根据位置设置
        index = (position + 1) % 9;
        switch (index) {
            case 1:
                holder.cir_img.setImageResource(R.mipmap.avatar1);
                break;
            case 2:
                holder.cir_img.setImageResource(R.mipmap.avatar2);
                break;
            case 3:
                holder.cir_img.setImageResource(R.mipmap.avatar3);
                break;
            case 4:
                holder.cir_img.setImageResource(R.mipmap.avatar4);
                break;
            case 5:
                holder.cir_img.setImageResource(R.mipmap.avatar5);
                break;
            case 6:
                holder.cir_img.setImageResource(R.mipmap.avatar6);
                break;
            case 7:
                holder.cir_img.setImageResource(R.mipmap.avatar7);
                break;
            case 8:
                holder.cir_img.setImageResource(R.mipmap.avatar8);
                break;
        }

        EMConversation emConversation = conversations.get(position);
        //最近一条消息
        EMMessage lastMessage = emConversation.getLastMessage();
        String userName = lastMessage.getUserName();
        holder.username.setText(userName);
        EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();

        //消息内容
        String message = body.getMessage();
        //未读消息内容
        int unreadMsgCount = emConversation.getUnreadMsgCount();

        if (unreadMsgCount == 0) {
            holder.tv_unread.setVisibility(View.GONE);

        } else if (unreadMsgCount > 99) {
            holder.tv_unread.setVisibility(View.VISIBLE);
            holder.tv_unread.setText(99 + "");

        } else {
            holder.tv_unread.setText(unreadMsgCount + "");
            holder.tv_unread.setVisibility(View.VISIBLE);

        }
        holder.tv_time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
        holder.tv_message.setText(message);
        //设置条目点击事件
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {

                listener.OnClick(conversations.get(position).getLastMessage().getUserName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversations == null ? 0 : conversations.size();
    }

    /**
     * 重写下两个方法获取索引
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView cir_img;

        private TextView username;
        private TextView tv_time;
        private TextView tv_unread;
        private TextView tv_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cir_img = itemView.findViewById(R.id.cir_img);
            username = itemView.findViewById(R.id.username);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_unread = itemView.findViewById(R.id.tv_unread);
            tv_message = itemView.findViewById(R.id.tv_message);


        }
    }

    private AddItemClickListener listener;

    public void setAddItemClickListener(AddItemClickListener listener) {
        this.listener = listener;
    }

    public interface AddItemClickListener {
        void OnClick(String username);
    }

}
