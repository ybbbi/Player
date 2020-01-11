package com.ybbbi.player.fragment.yuedanpage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ybbbi.player.BaseFragment;
import com.ybbbi.player.R;
import com.ybbbi.player.View.CustomView;
import com.ybbbi.player.adapter.yuedanAdapter;
import com.ybbbi.player.bean.YueDanBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ybbbi
 * 2019-12-05 19:36
 */
public class YuedanFragment extends BaseFragment implements YuedanMVP.YDview {

    @Bind(R.id.recylerview)
    RecyclerView recylerview;
    @Bind(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private yueDanPresenter presenter;
    private int offset = 0;
    private int size = 10;
    private List<YueDanBean.PlayListsBean> list = new ArrayList<>();
    private yuedanAdapter adapter;
    private boolean hasMore=true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        presenter = new yueDanPresenter(this);
        offset=0;
        list.clear();
        load();
        customView.setonLoadListener(new CustomView.onLoadListener() {
            @Override
            public void Reload() {
                load();
            }
        });
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                offset=0;
                list.clear();
                load();
            }
        });

        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadData(offset,size);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL,false);

        recylerview.setLayoutManager(manager);
        adapter = new yuedanAdapter(list);
        recylerview.setAdapter(adapter);
        //加载数据
    }

    private void load() {
        presenter.loadData(offset, size);
        customView.showLoadingView();
    }

    @Override
    public void success(List<YueDanBean.PlayListsBean> bean) {
        offset+=bean.size();
        hasMore=bean.size()==size;
        customView.showSuccessView();
        list.addAll(bean);
        adapter.notifyDataSetChanged();
        smartrefreshlayout.finishRefresh();
        if(hasMore){
            smartrefreshlayout.finishLoadMore();
        }else{
            smartrefreshlayout.finishLoadMoreWithNoMoreData();
        }

    }

    @Override
    public void fail(int code, Exception e) {
        customView.showErrorView();
    }
}
