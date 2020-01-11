package com.ybbbi.qqdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ybbbi.qqdemo.MainActivity;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.ToastUtils;
import com.ybbbi.qqdemo.presenter.LoginPresenter;
import com.ybbbi.qqdemo.view.Interface.LoginIView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginIView {


    private Button login;
    private EditText username;
    private EditText pwd;
    private TextView register;
    private LoginPresenter loginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
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
        username = (EditText) findViewById(R.id.et_username);
        pwd = (EditText) findViewById(R.id.et_pwd);
        register = (TextView) findViewById(R.id.tv_newuser);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    //获取用户输入信息
    private String getText(EditText text){
        return text.getText().toString().trim();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                //对用户名信息进行判断
                String name = getText(username);
                String passwd = getText(pwd);
                //交给presenter处理
                showLoading("正在登录。。。");
                loginPresenter.Login(name,passwd);
                break;
            case R.id.tv_newuser:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void getLoginState(String username, boolean isSuccess, String s) {
        if(isSuccess){
            //跳转到主界面
            dismissLoading();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else{
            ToastUtils.ShowMsg("登录失败请检查用户名或密码"+s,this);
        }
    }
}
