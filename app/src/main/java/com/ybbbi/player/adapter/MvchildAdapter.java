package com.ybbbi.player.adapter;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ybbbi.player.R;
import com.ybbbi.player.Util;
import com.ybbbi.player.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ybbbi
 * 2019-12-07 10:37
 */
public class MvchildAdapter extends RecyclerView.Adapter<MvchildAdapter.Childholder> {
    private List<VideoBean> list;

    public MvchildAdapter(List<VideoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Childholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_mvitem, null);

        return new Childholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Childholder holder, final int position) {
        VideoBean videoBean = list.get(position);
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getArtistName());
        holder.playCount.setText(videoBean.getDescription());

        Glide.with(holder.itemView.getContext()).load(videoBean.getPosterPic()).into(holder.ivPostimg);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Childholder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.viewbgs)
        View viewbgs;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
        TextView playCount;
        @Bind(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;

        public Childholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Point point = Util.computeImgSize(240, 135, itemView.getContext());
            ivPostimg.getLayoutParams().width=point.x;
            ivPostimg.getLayoutParams().height= point.y;
            ivPostimg.requestLayout();

            viewbgs.getLayoutParams().width=point.x;
            viewbgs.getLayoutParams().height= point.y;
            viewbgs.requestLayout();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.Onclick(Childholder.this.getLayoutPosition());
                }
            });
        }
    }
    public interface  onItemclickListener{
       void Onclick(int position);
    }
    private onItemclickListener listener;
    public  void setOnItemclickListener(onItemclickListener listener){
        this.listener=listener;
    }


}
