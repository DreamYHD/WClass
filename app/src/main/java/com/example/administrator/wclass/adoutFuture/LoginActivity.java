package com.example.administrator.wclass.adoutFuture;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_username_login)
    EditText editUsernameLogin;
    @BindView(R.id.edit_password_login)
    EditText editPasswordLogin;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget_login)
    TextView forgetLogin;
    @BindView(R.id.register_btn)
    TextView registerBtn;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login)
    public void onLoginClicked() {
    }

    @OnClick(R.id.forget_login)
    public void onForgetLoginClicked() {
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        Intent intent = new Intent(this,RegistActivity.class);
        startActivity(intent);
    }
}
