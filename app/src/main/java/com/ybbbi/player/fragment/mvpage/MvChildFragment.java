package com.ybbbi.player.fragment.mvpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ybbbi.player.BaseFragment;
import com.ybbbi.player.Global.Commonstants;
import com.ybbbi.player.R;
import com.ybbbi.player.View.CustomView;
import com.ybbbi.player.adapter.MvchildAdapter;
import com.ybbbi.player.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ybbbi
 * 2019-12-07 09:56
 */
public class MvChildFragment extends BaseFragment implements MvchildMVP.MvchildView{
    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    private int offset=0;
    private int size=10;
    private MvchildAdapter adapter;
    private ArrayList<VideoBean> beans;

    public static MvChildFragment getInstance(String code){
      MvChildFragment childFragment=new MvChildFragment();
      Bundle bundle = new Bundle();
      bundle.putString(Commonstants.bundle_child,code);
      childFragment.setArguments(bundle);
      return childFragment;
  }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        final String code = getArguments().getString(Commonstants.bundle_child);
        final ChildPresenter presenter = new ChildPresenter(this);
        presenter.loadData(code,offset,size);
        beans = new ArrayList<>();
        adapter = new MvchildAdapter(beans);
        recylerview.setAdapter(adapter);
        recylerview.setLayoutManager(new LinearLayoutManager(getContext()));
        customView.setonLoadListener(new CustomView.onLoadListener() {
            @Override
            public void Reload() {
                offset=0;
                customView.showLoadingView();
                presenter.loadData(code,offset,size);

            }
        });
        //条目点击
        adapter.setOnItemclickListener(new MvchildAdapter.onItemclickListener() {
            @Override
            public void Onclick(int position) {

            }
        });
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadData(code,offset,size);
            }
        });
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                offset=0;
                presenter.loadData(code,offset,size);

            }
        });
    }

    @Override
    public void onFail(int code, Exception e) {
        customView.showErrorView();
    }
boolean hasMore=true;
    @Override
    public void onSuccess(List<VideoBean> list) {
        beans.addAll(list);
        adapter.notifyDataSetChanged();
        offset+=size;
        hasMore=beans.size()==size;
        if(hasMore){
            smartrefreshlayout.finishLoadMore();
        }else{
            smartrefreshlayout.finishLoadMoreWithNoMoreData();

        }
        smartrefreshlayout.finishRefresh();
        customView.showSuccessView();
    }
}
