package com.ybbbi.qqdemo.view.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.event.MessageEvent;
import com.ybbbi.qqdemo.presenter.ContactPresenter;
import com.ybbbi.qqdemo.view.Interface.ContactIView;
import com.ybbbi.qqdemo.view.activity.ChatActivity;
import com.ybbbi.qqdemo.widget.ContactLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * ybbbi
 * 2020-01-11 23:04
 */
public class ContactFragment extends BaseFragment implements ContactIView {

    private ContactPresenter contactPresenter;
    private ContactLayout contact_list;
    private MyAdapter myAdapter;

    @Override
    protected void initView() {
        contactPresenter = new ContactPresenter(this);
        contact_list = view.findViewById(R.id.contact_list);
        contactPresenter.initContact();

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> contacts;

        public List<String> getContacts() {
            return contacts;
        }

        public void setContacts(List<String> contacts) {
            this.contacts = contacts;
        }

        public MyAdapter(List<String> contacts) {
            this.contacts = contacts;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        int index = 0;

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String text = contacts.get(position);
            index = (position + 1) % 9;
            holder.id.setText(text);
            holder.section.setText(StringUtils.getFirstChar(text));
            if (position == 0) {
                holder.section.setVisibility(View.VISIBLE);
            } else {
                //遇上一个首字母相同隐藏，不同显示
                String current = StringUtils.getFirstChar(text);
                String before = StringUtils.getFirstChar(contacts.get(position - 1));
                holder.section.setVisibility(before.equals(current) ? View.GONE : View.VISIBLE);
              /*  if(before.equals(current)){
                    holder.section.setVisibility(View.GONE);
                }else{
                    holder.section.setVisibility(View.VISIBLE);

                }*/

            }
            //条目内容点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //聊天界面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("contact",text);
                    startActivity(intent);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                        删除对话框
                    Snackbar.make(view, "确定要删除" + contacts.get(position) + "吗？", 5000).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                                ToastUtils.ShowMsg("已删除"+contacts.get(position),getContext());
                            contactPresenter.delete(contacts.get(position));
                        }
                    }).show();
                    return true;
                }
            });


            //根据索引设置头像
            switch (index) {
                case 1:
                    holder.id_img.setImageResource(R.mipmap.avatar1);
                    break;
                case 2:
                    holder.id_img.setImageResource(R.mipmap.avatar2);
                    break;
                case 3:
                    holder.id_img.setImageResource(R.mipmap.avatar3);
                    break;
                case 4:
                    holder.id_img.setImageResource(R.mipmap.avatar4);
                    break;
                case 5:
                    holder.id_img.setImageResource(R.mipmap.avatar5);
                    break;
                case 6:
                    holder.id_img.setImageResource(R.mipmap.avatar6);
                    break;
                case 7:
                    holder.id_img.setImageResource(R.mipmap.avatar7);
                    break;
                case 8:
                    holder.id_img.setImageResource(R.mipmap.avatar8);
                    break;
            }

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView id;
            private TextView section;
            private ImageView id_img;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.contact_id);
                id_img = itemView.findViewById(R.id.contact_img);
                section = itemView.findViewById(R.id.section);
            }
        }
    }

    @Override
    public int bindView() {
        return R.layout.contact;
    }

    @Override
    public void onInit(List<String> contacts) {
        myAdapter = new MyAdapter(contacts);
        contact_list.setAdapter(myAdapter);
    }

    @Override
    public void onUpdate(boolean isSuccess, List<String> contacts, String error) {
        if (isSuccess) {
            myAdapter.setContacts(contacts);
            myAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.ShowMsg("更新失败", getContext());
        }
    }

    @Override
    public void onDelete(boolean isSuccess, String s) {
        if(isSuccess){
            ToastUtils.ShowMsg("删除成功",getContext());
        }else{
            ToastUtils.ShowMsg("删除失败",getContext());

        }
    }
    //eventbus 传递事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContactChange(MessageEvent event) {


        contactPresenter.updateContact();
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


}
