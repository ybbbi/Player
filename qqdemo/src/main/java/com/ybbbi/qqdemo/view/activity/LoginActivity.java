package com.ybbbi.qqdemo.view.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.material.textfield.TextInputLayout;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.Utils.ThreadUtils;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.presenter.LoginPresenter;
import com.ybbbi.qqdemo.view.Interface.LoginIView;

import java.security.Permission;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginIView {


    private Button login;
    private EditText username;
    private TextInputLayout til_username;
    private TextInputLayout til_pwd;
    private EditText pwd;
    private TextView register;
    private LoginPresenter loginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        setSwipeBackEnable(false);
        init();
    }


    //如果启动模式为singletask复用之前创建的activity，不走oncreate方法直接调用onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        String username_txt = intent.getStringExtra("username");
        String pwd_txt = intent.getStringExtra("pwd");
        username.setText(username_txt);
        pwd.setText(pwd_txt);

    }

    private void init() {
        loginPresenter = new LoginPresenter(this);
        login = (Button) findViewById(R.id.btn_login);
        til_username = (TextInputLayout) findViewById(R.id.til_username);
        til_pwd = (TextInputLayout) findViewById(R.id.til_pwd);
        username = (EditText) findViewById(R.id.et_username);
        pwd = (EditText) findViewById(R.id.et_pwd);
        register = (TextView) findViewById(R.id.tv_newuser);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    //获取用户输入信息
    private String getText(EditText text) {
        return text.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //动态申请权限
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PermissionChecker.PERMISSION_GRANTED){

                    login();
                }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
                break;
            case R.id.tv_newuser:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
            login();
        }else{
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            startsettings();
            ToastUtils.ShowMsg("需要权限，请同意后重新打开",this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startsettings() {
        try {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);

        }catch (Throwable throwable){
            ToastUtils.ShowMsg("出现异常："+throwable,this);
        }
    }

    private void login() {
        //对用户名信息进行判断
        String name = getText(username);
        String passwd = getText(pwd);
        //交给presenter处理
        showLoading("正在登录。。。");
        chechUser(name,passwd);
        try {
        loginPresenter.Login(name, passwd);

        }catch (IllegalArgumentException e){
            dismissLoading();
        }
    }

    /**检查是否为空
     *
     * @param name
     * @param passwd
     */
    private void chechUser(String name, String passwd) {
        if(!StringUtils.CheckUserName(name)){
            til_username.setErrorEnabled(true);
            til_username.setError("用户名不能为空且不少于6个字符");

        }else {
            til_username.setErrorEnabled(false);
        }
        if(!StringUtils.CheckUserPwd(passwd)){
            til_pwd.setErrorEnabled(true);
            til_pwd.setError("密码不能为空且长度为6-18");

        }else{
            til_pwd.setErrorEnabled(false);

        }

    }

    @Override
    public void getLoginState(String username, boolean isSuccess, String s) {
        if (isSuccess) {
            //跳转到主界面
            dismissLoading();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            ThreadUtils.runOnMainThread(() ->
                    {
                        dismissLoading();
                        ToastUtils.ShowMsg("登录失败请检查用户名或密码", LoginActivity.this);

                    }
            );
        }
    }
}
