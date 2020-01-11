package com.ybbbi.player.fragment.homepage;

import com.ybbbi.player.URLProviderUtil;
import com.ybbbi.player.bean.VideoBean;
import com.ybbbi.player.http.BaseCallBack;
import com.ybbbi.player.http.HttpManager;
import com.ybbbi.player.utils.LogUtils;

import java.util.List;

/**
 * Created by Ding on 2016/12/19.
 */
public class HomePresenter implements HomeMvp.Presenter {
    private static final String TAG = "HomePresenter";

    HomeMvp.View view;

    public HomePresenter(HomeMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int offset, int size) {
        LogUtils.e(TAG,"HomePresenter.loadData,开始加载数据");

        String url = URLProviderUtil.getMainPageUrl(offset,size);
        LogUtils.e(HomePresenter.class,"url="+url);
        HttpManager.getInstance().get(url, new BaseCallBack<List<VideoBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(List<VideoBean> videoBeen) {
                LogUtils.e(TAG,"HomePresenter.onSuccess,成功获取到数据");
                view.setData(videoBeen);
                //存储url数据
                saveUrlData(videoBeen);


            }
        });
    }

    /**
     *
     * @param videoBeen
     */
    private void saveUrlData(List<VideoBean> videoBeen) {

    }
}
