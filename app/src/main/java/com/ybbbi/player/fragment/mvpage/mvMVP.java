package com.ybbbi.player.fragment.mvpage;

import com.ybbbi.player.bean.AreaBean;

import java.util.List;

/**
 * ybbbi
 * 2019-12-06 19:30
 */
public class mvMVP {
    interface mvPresenter{
        void loadData();
    }
    interface mvView{
        void onsuccess(List<AreaBean> list);
        void fail(int code, Exception e);
    }
}
