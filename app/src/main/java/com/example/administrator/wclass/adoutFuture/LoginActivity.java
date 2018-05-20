package com.example.administrator.wclass.adoutFuture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;
import com.example.administrator.wclass.utils.ActivityUtils;

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
    @BindView(R.id.forget_login_text)
    TextView forgetLogin;
    @BindView(R.id.register_btn_text)
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
        String phone_number = editUsernameLogin.getText().toString().trim();
        String password = editPasswordLogin.getText().toString().trim();
        AVUser.loginByMobilePhoneNumberInBackground(phone_number, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null){
                    Toast.makeText(activity,"登陆成功",Toast.LENGTH_SHORT).show();
                    final_user = AVUser.getCurrentUser();
                    activity.finish();

                }else {
                    Snackbar.make(findViewById(R.id.login),"登陆失败"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    @OnClick(R.id.forget_login_text)
    public void onForgetLoginClicked() {
    }

    @OnClick(R.id.register_btn_text)
    public void onRegisterBtnClicked() {
        Intent intent = new Intent(this,RegistActivity.class);
        startActivity(intent);

    }
}
