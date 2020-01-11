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
import com.ybbbi.player.bean.YueDanBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ybbbi
 * 2019-12-06 17:26
 */
public class yuedanAdapter extends RecyclerView.Adapter<yuedanAdapter.mViewHolder> {


    private List<YueDanBean.PlayListsBean> list;

    public yuedanAdapter(List<YueDanBean.PlayListsBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_yuedanitem, null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        YueDanBean.PlayListsBean yueDanBean = list.get(position);
        holder.title.setText(yueDanBean.getTitle());
        holder.author.setText(yueDanBean.getCreator().getNickName());
        holder.playCount.setText("收录mv" + yueDanBean.getVideoCount() + "首");

        Glide.with(holder.itemView.getContext()).load(yueDanBean.getThumbnailPic()).into(holder.ivPostimg);
        Glide.with(holder.itemView.getContext()).load(yueDanBean.getPlayListPic()).into(holder.civImg);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.civ_img)
        CircleImageView civImg;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
        TextView playCount;
        @Bind(R.id.item_root)
        RelativeLayout itemRoot;

        @Bind(R.id.bg)
        View bg;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            Point point = Util.computeImgSize(240, 135, itemView.getContext());
            ivPostimg.getLayoutParams().width = point.x;
            ivPostimg.getLayoutParams().height = point.y;
            ivPostimg.requestLayout();
            bg.getLayoutParams().width = point.x;
            bg.getLayoutParams().height = point.y;
            bg.requestLayout();


        }
    }


}
