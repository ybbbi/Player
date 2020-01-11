package com.ybbbi.player.fragment.mvpage;

import com.ybbbi.player.URLProviderUtil;
import com.ybbbi.player.bean.MVDetailBean;
import com.ybbbi.player.bean.MvListBean;
import com.ybbbi.player.bean.VideoBean;
import com.ybbbi.player.http.BaseCallBack;
import com.ybbbi.player.http.HttpManager;

import java.util.List;

/**
 * ybbbi
 * 2019-12-07 10:26
 */
public class ChildPresenter implements MvchildMVP.Mvchildpresenter {
    private MvchildMVP.MvchildView view;

    public ChildPresenter(MvchildMVP.MvchildView view) {
        this.view = view;
    }

    @Override
    public void loadData(String code, int offset, int size) {
            String url= URLProviderUtil.getMVListUrl(code,offset,size);
        HttpManager.getInstance().get(url, new BaseCallBack<MvListBean>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onFail(code, e);
            }

            @Override
            public void onSuccess(MvListBean bean) {
                view.onSuccess(bean.getVideos());
            }

        });
    }
}
