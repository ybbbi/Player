package com.ybbbi.qqdemo.view.fragment;

import android.widget.TextView;

import com.ybbbi.qqdemo.R;

/**
 * ybbbi
 * 2020-01-11 23:03
 */
public class MessageFragment extends BaseFragment {
    @Override
    protected void initView() {
        TextView view = this.view.findViewById(R.id.text);
        view.setText("消息");
    }

    @Override
    public int bindView() {
        return R.layout.basefragment;
    }
}
