package com.ybbbi.player.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ybbbi.player.R;

/**
 * Created by Ding on 2016/12/19.
 */
public class TestFragment extends Fragment {

    /**
     * 获取 Fragment 对象
     * @param content
     * @return
     */
    public static TestFragment newInstance(String content){
        // 填充初始化参数
        Bundle args = new Bundle();
        args.putString("content",content);

        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(args);

        return testFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,null);
        TextView tv_test = (TextView) view.findViewById(R.id.tv_test);

        // 获取初始化参数
        Bundle args = getArguments();
        String content = args.getString("content");
        tv_test.setText(content);
        return view;
    }
}
