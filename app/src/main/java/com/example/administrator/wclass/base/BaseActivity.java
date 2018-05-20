package com.example.administrator.wclass.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVUser;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/29.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected FragmentManager fragmentManager;
    protected Activity activity;
    protected AVUser final_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        ButterKnife.bind(this);
        if (AVUser.getCurrentUser() != null){
            final_user = AVUser.getCurrentUser();
        }
        fragmentManager = getSupportFragmentManager();
        activity = this;
        logicActivity(savedInstanceState);
        
    }
    protected abstract void logicActivity(Bundle savedInstanceState);
    protected abstract int getLayoutView();
}
