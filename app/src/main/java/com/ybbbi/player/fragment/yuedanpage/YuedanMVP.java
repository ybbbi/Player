package com.ybbbi.player.fragment.yuedanpage;

import com.ybbbi.player.bean.YueDanBean;

import java.util.List;

/**
 * ybbbi
 * 2019-12-05 19:43
 */
public interface YuedanMVP {
   interface YDpresenter{
       void loadData( int offset,int size);
   }
   interface  YDview{
       void success(List<YueDanBean.PlayListsBean> bean);
       void fail(int code, Exception e);
   }

}
