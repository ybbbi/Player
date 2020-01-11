package com.ybbbi.player.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.ybbbi.player.R;
import com.ybbbi.player.activity.PlayerActivity;
import com.ybbbi.player.activity.WebViewActivity;
import com.ybbbi.player.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<VideoBean> list;
    private Context mcontext;

    /**
     * 构造方法传入数据
     */
    public HomeAdapter(List<VideoBean> videoBeen) {
        list = videoBeen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        View itemView = View.inflate(mcontext, R.layout.homepage_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        VideoBean videoBean = list.get(position);
        holder.tvTitle.setText(videoBean.getTitle());

        holder.tvDescription.setText(videoBean.getDescription());
//        LogUtils.e(HomeAdapter.class,videoBean.getPosterPic());
        //上下文可以用 holder.itemView.getContext()获取

//图片加载
        DrawableCrossFadeFactory.Builder builder = new DrawableCrossFadeFactory.Builder(800);

        DrawableTransitionOptions options =
                new DrawableTransitionOptions().crossFade(builder);


        Glide.with(mcontext)
                .load(videoBean.getThumbnailPic())
               // .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(options)
                .into(holder.ivItemLogo);
        defineTag(videoBean, holder);

    }

    /**
     * 辨别每个项目的类型
     *
     * @param videoBean
     * @param holder
     */
    private void defineTag(VideoBean videoBean, MyViewHolder holder) {
        String type = videoBean.getType();
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            holder.tag = 0;
            holder.ivType.setImageResource(R.drawable.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            holder.tag = 1;
            holder.ivType.setImageResource(R.drawable.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            holder.tag = 2;
            holder.ivType.setImageResource(R.drawable.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            holder.tag = 3;
            holder.ivType.setImageResource(R.drawable.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            holder.tag = 4;
            holder.ivType.setImageResource(R.drawable.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            holder.tag = 5;
            holder.ivType.setImageResource(R.drawable.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            holder.tag = 6;
            holder.ivType.setImageResource(R.drawable.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            holder.tag = 7;
            holder.ivType.setImageResource(R.drawable.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            holder.tag = 8;
            holder.ivType.setImageResource(R.drawable.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            holder.tag = 9;
            holder.ivType.setImageResource(R.drawable.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(videoBean.getType())) {//打开页面
            holder.tag = 10;
            holder.ivType.setImageResource(R.drawable.home_page_project);
        } else {
            holder.tag = -100;
            holder.ivType.setImageResource(0);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Intent mIntent;
        @Bind(R.id.iv_item_logo)
        ImageView ivItemLogo;
        @Bind(R.id.iv_contentimg)
        ImageView ivContentimg;
        @Bind(R.id.viewbg)
        View viewbg;
        @Bind(R.id.iv_type)
        ImageView ivType;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;
        @Bind(R.id.homepage_ll)
        LinearLayout homepage_ll;

        int tag;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //获取图片适配宽高
            Point point = displayHeight(240, 135);
            ViewGroup.LayoutParams layoutParams = ivContentimg.getLayoutParams();
            layoutParams.width = point.x;
            layoutParams.height = point.y;
            ivContentimg.setLayoutParams(layoutParams);

            //讲背景也设置同样宽高
            //如果使用根布局会空指针
            ViewGroup.LayoutParams viewbgLayoutParams = viewbg.getLayoutParams();
            viewbgLayoutParams.width = point.x;
            viewbgLayoutParams.height = point.y;
            viewbg.setLayoutParams(viewbgLayoutParams);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    VideoBean videoBean = list.get(position);
                    switch (tag) {
                        case 0:
                        case 4:
                        case 10:
                            mIntent = new Intent(mcontext, WebViewActivity.class);
                            mIntent.putExtra("url", videoBean.getUrl());
                            mcontext.startActivity(mIntent);
                            break;
                        case 1:
                        case 5:
                        case 7:
                            mIntent = new Intent(mcontext, PlayerActivity.class);
                            mIntent.putExtra("url", videoBean.getUrl());
                            mIntent.putExtra("title", videoBean.getTitle());
                            mIntent.putExtra("imgurl", videoBean.getThumbnailPic());
                            mcontext.startActivity(mIntent);
                            break;

                        case 2:
                        case 3:
                            break;
                    }
//                    ToastUtils.show( videoBean.getHdUrl());
                }
            });
        }

        /**
         * @param width  预测的宽度
         * @param height 预测的高度
         * @return 经过适配的宽高
         */

        private Point displayHeight(int width, int height) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) mcontext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int widthPixels = metrics.widthPixels;
            int heightPixels = widthPixels * height / width;
            Point point = new Point(widthPixels, heightPixels);
            return point;
        }
    }
}
