package com.ybbbi.player.fragment.mvpage;

import com.ybbbi.player.bean.MVDetailBean;
import com.ybbbi.player.bean.MvListBean;
import com.ybbbi.player.bean.VideoBean;

import java.util.List;

/**
 * ybbbi
 * 2019-12-07 10:25
 */
public interface MvchildMVP {
    interface  Mvchildpresenter{
        void loadData(String code, int offset, int size);
    }
    interface  MvchildView{


        void onFail(int code, Exception e);



        void onSuccess(List<VideoBean>list);
    }
}
