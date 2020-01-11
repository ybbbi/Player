package com.ybbbi.player.fragment.mvpage;

import com.ybbbi.player.URLProviderUtil;
import com.ybbbi.player.bean.AreaBean;
import com.ybbbi.player.http.BaseCallBack;
import com.ybbbi.player.http.HttpManager;

import java.util.List;

/**
 * ybbbi
 * 2019-12-06 19:31
 */
public class Presenter implements mvMVP.mvPresenter {
    private mvMVP.mvView view;

    public Presenter(mvMVP.mvView view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        String url = URLProviderUtil.getMVareaUrl();
        HttpManager.getInstance().get(url, new BaseCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.fail(code,e);
            }

            @Override
            public void onSuccess(List<AreaBean> list) {

                view.onsuccess(list);
            }
        });
    }
}
