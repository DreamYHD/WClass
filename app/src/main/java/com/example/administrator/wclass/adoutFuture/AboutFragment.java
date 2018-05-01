package com.example.administrator.wclass.adoutFuture;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends BaseFragment {


    @BindView(R.id.me_image_avatar)
    ImageView meImageAvatar;
    @BindView(R.id.me_name)
    TextView meName;
    @BindView(R.id.me_major)
    TextView meMajor;
    @BindView(R.id.me_login)
    ImageView meLogin;
    @BindView(R.id.me_score_text)
    TextView meScoreText;
    @BindView(R.id.me_joinnum_text)
    TextView meJoinnumText;
    @BindView(R.id.about_btn)
    RelativeLayout aboutBtn;
    @BindView(R.id.setting_btn)
    RelativeLayout settingBtn;
    @BindView(R.id.logout_btn)
    RelativeLayout logoutBtn;

    public static AboutFragment getInstance() {
        // Required empty public constructor
        return new AboutFragment();
    }

    @Override
    protected void logic() {

    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {

    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_about;
    }


    @OnClick(R.id.me_image_avatar)
    public void onMeImageAvatarClicked() {
    }

    @OnClick(R.id.me_login)
    public void onMeLoginClicked() {
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.about_btn)
    public void onAboutBtnClicked() {
    }

    @OnClick(R.id.setting_btn)
    public void onSettingBtnClicked() {
    }

    @OnClick(R.id.logout_btn)
    public void onLogoutBtnClicked() {
    }
}
