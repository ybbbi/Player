package com.ybbbi.qqdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.presenter.RegisterPresenter;
import com.ybbbi.qqdemo.view.Interface.RegisterIView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterIView {

    //判断是否合法
    private boolean userBoolean;
    private boolean pwdBoolean;
    private boolean confirm_Boolean;

    private Button regist;
    private EditText username;
    private TextInputLayout til_username;
    private TextInputLayout til_pwd;
    private TextInputLayout til_confirmpwd;
    private EditText pwd;
    private EditText et_confirmpwd;
    private RegisterPresenter registerPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        init();
        initListener();
    }

    /**
     * 成功注册
     *
     * @param s
     * @param username
     * @param pwd
     */
    @Override
    public void Success(String s, String username, String pwd) {
        //跳转到loginactivity
        dismissLoading();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("pwd", pwd);

        startActivity(intent);

    }

    /**
     * 注册失败
     *
     * @param s
     */
    @Override
    public void Fail(String s) {
        dismissLoading();
        Toast.makeText(this, "注册失败，请联系客服人员：XXXX-XXXX", Toast.LENGTH_LONG).show();

    }


    private class MyTextWatcherAdapter implements TextWatcher {


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    }

    private void init() {
        registerPresenter = new RegisterPresenter(this);

        username = (EditText) findViewById(R.id.et_username);
        regist = (Button) findViewById(R.id.btn_regist);
        pwd = (EditText) findViewById(R.id.et_pwd);
        et_confirmpwd = (EditText) findViewById(R.id.et_confirmpwd);

        til_username = (TextInputLayout) findViewById(R.id.til_username);
        til_pwd = (TextInputLayout) findViewById(R.id.til_pwd);
        til_confirmpwd = (TextInputLayout) findViewById(R.id.til_confirmpwd);

        regist.setOnClickListener(this);

    }

    private void initListener() {

        username.addTextChangedListener(new MyTextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                userBoolean = StringUtils.CheckUserName(editable.toString().trim());
                til_username.setError("用户名长度为6-18，并且不为空");
                til_username.setErrorEnabled(!userBoolean);
            }
        });
        pwd.addTextChangedListener(new MyTextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                pwdBoolean = StringUtils.CheckUserPwd(editable.toString().trim());

                //不合理给出提示信息
                til_pwd.setError("首字符为字母大写，长度6-18");

                til_pwd.setErrorEnabled(!pwdBoolean);
                if (!((et_confirmpwd.getText().toString().trim()).equals(""))) {
                    checkPWD(editable, et_confirmpwd);
                }
            }

        });

        et_confirmpwd.addTextChangedListener(new MyTextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                checkPWD(editable, pwd);

            }
        });
    }

    private void checkPWD(Editable editable, EditText text) {
        confirm_Boolean = ((editable.toString().trim()).equals(text.getText().toString().trim()));
        til_confirmpwd.setError("两次密码输入不一致");
        til_confirmpwd.setErrorEnabled(!confirm_Boolean);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regist:
                //获取信息
                String name = username.getText().toString().trim();
                String passwd = pwd.getText().toString().trim();
                if (userBoolean && confirm_Boolean && pwdBoolean) {
                    //发送消息,交给p层处理
                    showLoading("注册中，请稍后。。");
                    registerPresenter.OnRegister(name, passwd);
                }
                //发送数据
                break;
        }
    }
}
