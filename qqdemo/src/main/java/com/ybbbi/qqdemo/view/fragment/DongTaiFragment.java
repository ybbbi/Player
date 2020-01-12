package com.ybbbi.qqdemo.view.fragment;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

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
        if (isSuccess) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            ToastUtils.ShowMsg(s, getActivity());
        }
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(R.string.logout);
        if(Build.VERSION.SDK_INT>=24){
        builder.setTitle(Html.fromHtml("<font  color='#c55553'>退出账户</font>", Html.FROM_HTML_MODE_LEGACY));

        }else{

        builder.setTitle(Html.fromHtml("<font color='#c55553'>退出账户</font>"));
        }
        builder.setMessage(R.string.logout);
        //<font color=\"#C55553\">退出账户</font>
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                showDialog("退出登录中。。。");
                logoutPresenter.Logout();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}
