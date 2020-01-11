package com.ybbbi.qqdemo.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ybbbi.qqdemo.R;

import java.util.zip.Inflater;

/**
 * ybbbi
 * 2020-01-11 22:20
 */
public abstract class BaseFragment extends Fragment {

    protected  View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(bindView(), null);

        }
        initView();
        return view;
    }

    protected abstract void initView();


    public abstract int bindView();
}
