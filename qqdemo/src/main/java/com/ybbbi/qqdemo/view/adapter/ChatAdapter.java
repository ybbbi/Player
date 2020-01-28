package com.ybbbi.qqdemo.view.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.ybbbi.qqdemo.R;

import java.util.Date;
import java.util.List;

/**
 * ybbbi
 * 2020-01-27 16:07
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewholder> {
    public static final int RECEIVE = 0;
    public static final int SEND = 1;
    private List<EMMessage> messages;

    public void setMessages(List<EMMessage> messages) {
        this.messages = messages;
    }

    public ChatAdapter(List<EMMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == RECEIVE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat_send_item, parent, false);

        }
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewholder holder, int position) {
        EMMessage emMessage = messages.get(position);
        long msgTime = emMessage.getMsgTime();
        //是否显示时间
        if (position == 0) {
            if (DateUtils.isCloseEnough(msgTime, System.currentTimeMillis())) {
                holder.tv_time.setVisibility(View.GONE);
            } else {
                if(msgTime!=0){
                holder.tv_time.setText(DateUtils.getTimestampString(new Date(msgTime)));

                }
                holder.tv_time.setVisibility(View.VISIBLE);
            }
        } else {
            if (DateUtils.isCloseEnough(msgTime, messages.get(position-1).getMsgTime())) {
                holder.tv_time.setVisibility(View.GONE);
            } else {
                holder.tv_time.setText(DateUtils.getTimestampString(new Date(msgTime)));
                holder.tv_time.setVisibility(View.VISIBLE);
            }

        }
        EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
        String msg = body.getMessage();
        holder.tv_message.setText(msg);
        if (emMessage.direct()==EMMessage.Direct.SEND){
            EMMessage.Status status = emMessage.status();
            switch (status){
                case SUCCESS:
                    holder.iv_state.setVisibility(View.GONE);
                    break;
                case FAIL:
                    holder.iv_state.setImageResource(R.mipmap.msg_error);
                    break;
                case INPROGRESS:
                    holder.iv_state.setImageResource(R.drawable.loading);
                    AnimationDrawable drawable = (AnimationDrawable) holder.iv_state.getDrawable();
                    drawable.start();
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage.Direct direct = messages.get(position).direct();
        if (direct == EMMessage.Direct.RECEIVE) {
            return RECEIVE;
        } else {
            return SEND;
        }


    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_message;
        ImageView iv_state;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tv_message = (TextView)itemView.findViewById(R.id.tv_message);
            tv_time =(TextView) itemView.findViewById(R.id.tv_time);
            iv_state = (ImageView) itemView.findViewById(R.id.iv_state);

        }
    }
}
