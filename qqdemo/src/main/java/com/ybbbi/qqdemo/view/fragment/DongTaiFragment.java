package com.ybbbi.qqdemo.view.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.presenter.LogoutPresenter;
import com.ybbbi.qqdemo.view.Interface.LogoutIView;
import com.ybbbi.qqdemo.view.activity.LoginActivity;

/**
 * ybbbi
 * 2020-01-11 23:04
 */
public class DongTaiFragment extends BaseFragment implements LogoutIView, View.OnClickListener {


    private Button btn_logout;
    private LogoutPresenter logoutPresenter;
    private TextView user;

    @Override
    protected void initView() {
        logoutPresenter = new LogoutPresenter(this);
        user = view.findViewById(R.id.user);
        user.setText(EMClient.getInstance().getCurrentUser());
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public int bindView() {
        return R.layout.dongtai_fragment;
    }

    @Override
    public void onLogout(boolean isSuccess, String s) {
        dissmissDialog();
        if(isSuccess){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }else{
            ToastUtils.ShowMsg(s,getActivity());
        }
    }

    @Override
    public void onClick(View view) {
        showDialog("退出登录中。。。");
        logoutPresenter.Logout();
    }
}
