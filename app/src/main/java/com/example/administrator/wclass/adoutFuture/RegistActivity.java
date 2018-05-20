package com.example.administrator.wclass.adoutFuture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {


    private static final String TAG = "RegistActivity";
    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.username_register)
    EditText usernameRegister;
    @BindView(R.id.password_register)
    EditText passwordRegister;
    @BindView(R.id.phone_register)
    EditText phoneRegister;
    @BindView(R.id.st_number)
    EditText stNumber;
    @BindView(R.id.school_register)
    EditText schoolRegister;
    @BindView(R.id.major_register)
    EditText majorRegister;
    @BindView(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_regist;
    }


    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
        activity.finish();
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        String username = usernameRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();
        String phone_number = phoneRegister.getText().toString().trim();
        String st_number = stNumber.getText().toString().trim();
        String school = schoolRegister.getText().toString().trim();
        String major = majorRegister.getText().toString().trim();
        AVUser avUser = new AVUser();
        avUser.setMobilePhoneNumber(phone_number);
        avUser.setUsername(username);
        avUser.setPassword(password);
        avUser.put("st_number",st_number);
        avUser.put("school",school);
        avUser.put("major",major);
        avUser.put("all_rank",0);
        avUser.put("all_class",0);
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    Log.i(TAG, "done: success login");
                    Snackbar.make(findViewById(R.id.register_btn),"注册成功",Snackbar.LENGTH_LONG).
                            setAction("点击登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(activity,LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                }else {
                    Snackbar.make(findViewById(R.id.register_btn),"注册失败"+e.getMessage(),Snackbar.LENGTH_LONG)
                           .show();
                }
            }
        });
    }
}
