package com.ybbbi.qqdemo.view.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ybbbi.qqdemo.R;

/**
 * ybbbi
 * 2020-01-11 23:03
 */
public class MessageFragment extends BaseFragment {

    private RecyclerView recyclerview;
    private FloatingActionButton fab;

    @Override
    protected void initView() {
        recyclerview = view.findViewById(R.id.recyclerview);
        fab = view.findViewById(R.id.fab);

    }

    @Override
    public int bindView() {
        return R.layout.conversation;
    }
}
