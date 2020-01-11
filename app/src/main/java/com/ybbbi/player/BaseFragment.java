package com.ybbbi.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ybbbi.player.Global.Commonstants;
import com.ybbbi.player.View.CustomView;
import com.ybbbi.player.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by Ding on 2016/12/19.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected int offSet = 0;//当前索引

    protected int ammount = 10;//请求数量
    protected CustomView customView;
    private String child;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e(getClass(), "BaseFragment.onCreateView,rootView=" + rootView);
        if (customView == null) {
            customView = new CustomView(getContext());

        }
        // 复用 rootview
      /*  if (rootView==null){
            rootView = inflater.inflate(getLayoutId(),null);
        }*/
        customView.bindSuccessView(getLayoutId());
        ButterKnife.bind(this, customView);





        initView();
        return customView;
    }

    /**
     * 返回当前 Fragment使用的布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 处理界面初始化
     */
    protected abstract void initView();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);


    }
}
