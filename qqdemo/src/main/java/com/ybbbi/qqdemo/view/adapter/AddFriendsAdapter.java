package com.ybbbi.qqdemo.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMClient;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ybbbi
 * 2020-01-20 10:13
 */
public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.MyViewHolder> {

    private int index;
    //从bmob 服务器返回的数据
    private List<User> userArrayList = new ArrayList<>();
    //从环信返回的好友数据
    private List<String> friendArrayList = new ArrayList<>();
    private User user;

    public void setFriendArrayList(List<String> friendArrayList) {
        if (friendArrayList != null) {

            this.friendArrayList = friendArrayList;
        }
    }

    public void setUserArrayList(List<User> userArrayList) {
        if (userArrayList != null) {

            this.userArrayList = userArrayList;
        }
    }

    public AddFriendsAdapter(List<User> userArrayList, List<String> friendArrayList) {

        this.userArrayList = userArrayList;
        this.friendArrayList = friendArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_addf, null);

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

        //设置姓名
        user = userArrayList.get(position);
        holder.name.setText(user.username);
        holder.time.setText(user.getCreatedAt());
        boolean b = friendArrayList.contains(user.username);
        holder.btn_add.setOnClickListener(view -> {
            if (b){
                ToastUtils.ShowMsg(holder.btn_add.getText().toString() + user.username, holder.itemView.getContext());

            }else{
                //添加好友,将点击事件传给view
                if(listener!=null){
                    listener.OnClick(user.username);
                }
//                ToastUtils.ShowMsg(holder.btn_add.getText().toString() + user.username, holder.itemView.getContext());

            }
        });
        if (b) {
            //已经是好友了
            holder.btn_add.setClickable(false);
            holder.btn_add.setText(R.string.isfriends);
            holder.btn_add.setBackgroundResource(R.drawable.addfriend_btn_pressed);

        } else {
            holder.btn_add.setBackgroundResource(R.drawable.addfriend_btn_normal);
            holder.btn_add.setClickable(true);
            holder.btn_add.setText(R.string.add);
        }


    }

    @Override
    public int getItemCount() {
        return userArrayList == null ? 0 : userArrayList.size();
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
        private Button btn_add;
        private TextView name;
        private TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cir_img = itemView.findViewById(R.id.cir_img);
            btn_add = itemView.findViewById(R.id.addfriends);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);


        }
    }
    private AddFriensClickListener listener;
    public void setAddFriensClickListener(AddFriensClickListener listener){
        this.listener=listener;
    }
    public interface AddFriensClickListener{
        void OnClick(String username);
    }

}
