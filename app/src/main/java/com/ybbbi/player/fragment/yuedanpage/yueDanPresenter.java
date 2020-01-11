package com.ybbbi.player.fragment.yuedanpage;

import com.ybbbi.player.URLProviderUtil;
import com.ybbbi.player.Util;
import com.ybbbi.player.bean.YueDanBean;
import com.ybbbi.player.http.BaseCallBack;
import com.ybbbi.player.http.HttpManager;

import java.util.List;

/**
 * ybbbi
 * 2019-12-05 19:50
 */
public class yueDanPresenter implements YuedanMVP.YDpresenter {
    private YuedanMVP.YDview view;

    public yueDanPresenter(YuedanMVP.YDview view) {
        this.view = view;
    }

    @Override
    public void loadData( int offset,int size) {
        String url= URLProviderUtil.getMainPageYueDanUrl(offset,size);
        HttpManager.getInstance().get(url, new BaseCallBack<List<YueDanBean.PlayListsBean>>() {
            @Override
            public void onFailure(int code, Exception e) {

                view.fail(code,e);
            }

            @Override
            public void onSuccess(List<YueDanBean.PlayListsBean> bean) {
                view.success(bean);
            }
        });
    }
}
