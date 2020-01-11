package com.ybbbi.player.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ybbbi.player.R;
import com.ybbbi.player.fragment.homepage.HomeMvp;

/**
 * 自定义主界面（三种状态）
 */
public class CustomView extends FrameLayout {
    //错误界面
    private View errorView;
    //加载界面
    private View loadingView;
    //成功界面
    private View successView;

    //成功layout ID
    int sID;

    public CustomView(@NonNull Context context) {
        this(context, null);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //绑定错误界面
        loadingView = View.inflate(getContext(), R.layout.loadingview, null);
        addView(loadingView);
        errorView = View.inflate(getContext(), R.layout.errorview, null);
        addView(errorView);
        //绑定加载界面
        //绑定成功界面
        showLoadingView();
        Button error_btn = errorView.findViewById(R.id.errorview_btn);
        error_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.Reload();

                }
            }
        });


    }

    /**
     * 绑定显示的布局
     *
     * @param id 布局id
     */
    public void bindSuccessView(int id) {
        successView = View.inflate(getContext(), id, null);
        if (successView != null) {
            successView.setVisibility(View.GONE);
            addView(successView);

        }
    }

    public void showErrorView(){
        hideAllView();
        errorView.setVisibility(View.VISIBLE);
    }
    public void showSuccessView(){

        hideAllView();
        if(successView!=null){
        successView.setVisibility(View.VISIBLE);
        }
    }
public void showLoadingView(){
        hideAllView();
        loadingView.setVisibility(View.VISIBLE);
}
    //函数回调
    public  interface onLoadListener {
        void Reload();
    }

    private onLoadListener listener;

    public void setonLoadListener(onLoadListener listener) {
        this.listener = listener;
    }

    /**
     * 隐藏所有view
     */
    public void hideAllView() {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        if (successView != null) {
            successView.setVisibility(View.GONE);
        }
    }


}
