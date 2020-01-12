package com.ybbbi.qqdemo.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.presenter.ContactPresenter;
import com.ybbbi.qqdemo.view.Interface.ContactIView;
import com.ybbbi.qqdemo.widget.ContactLayout;

import java.util.List;
import java.util.zip.Inflater;

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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> contacts;

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

        private class MyViewHolder extends RecyclerView.ViewHolder {

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
}
