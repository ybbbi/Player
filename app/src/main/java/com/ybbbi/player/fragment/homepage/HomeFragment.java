package com.ybbbi.player.fragment.homepage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ybbbi.player.BaseFragment;
import com.ybbbi.player.R;
import com.ybbbi.player.View.CustomView;
import com.ybbbi.player.adapter.HomeAdapter;
import com.ybbbi.player.bean.VideoBean;
import com.ybbbi.player.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Ding on 2016/12/19.
 */
public class HomeFragment extends BaseFragment implements HomeMvp.View {


    private boolean hasMore = true;


    private static final String TAG = "HomeFragment";
    @Bind(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private List<VideoBean> list = new ArrayList<>();
    @Bind(R.id.recylerview)
    RecyclerView recylerview;

    private HomeMvp.Presenter presenter;
    private HomeAdapter homeAdapter;

    @Override
    protected int getLayoutId() {




        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {


        //给recyclerview设置';
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerview.setLayoutManager(layoutManager);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        recylerview.setLayoutManager(staggeredGridLayoutManager);
        homeAdapter = new HomeAdapter(list);
        recylerview.setAdapter(homeAdapter);


        LogUtils.e(TAG, "HomeFragment.initView,创建 presenter,并请求数据");
        presenter = new HomePresenter(this);
        load();
        //刷新界面

        customView.setonLoadListener(new CustomView.onLoadListener() {
            @Override
            public void Reload() {
                load();
            }
        });

        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                offSet = 0;
                presenter.loadData(offSet, ammount);



            }
        });
        //传统刷新
//        smartrefreshlayout.setRefreshHeader(new ClassicsHeader(getContext()));

        //加载更多
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadData(offSet, ammount);

            }
        });
//        smartrefreshlayout.setRefreshFooter(new ClassicsFooter(getContext()));


        // 填充 RecylerView 列表

    }

    private void load() {
        presenter.loadData(offSet, ammount);
        customView.showLoadingView();
    }

    @Override
    public void setData(List<VideoBean> videoBeen) {
      /*  ArrayList<String> alist =new ArrayList<>();
        for (int i=0;i<videoBeen.size(); i++){
            alist.add(videoBeen.get(i).getType());
        }
        LogUtils.e(HomeFragment.class,"list="+alist.toString());*/

        customView.showSuccessView();

        offSet += videoBeen.size();

        hasMore = videoBeen.size() == offSet;
        list.addAll(videoBeen);
        homeAdapter.notifyDataSetChanged();
        smartrefreshlayout.finishRefresh();
        smartrefreshlayout.finishLoadMore();
//        LogUtils.e(TAG, "HomeFragment.setData,videoBeen=" + videoBeen.size());
    }

    @Override
    public void onError(int code, Exception e) {
        customView.showErrorView();
    }

}
