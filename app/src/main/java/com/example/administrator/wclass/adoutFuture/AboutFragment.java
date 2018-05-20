package com.example.administrator.wclass.adoutFuture;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
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


    private static final String TAG = "AboutFragment";
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
    Button logoutBtn;

    public static AboutFragment getInstance() {
        // Required empty public constructor
        return new AboutFragment();
    }

    @Override
    public void onStart() {
        final_user = AVUser.getCurrentUser();
        if (final_user != null){
            meName.setText(final_user.getUsername().toString());
            meMajor.setText(final_user.getString("school")+" "+final_user.get("major"));
            meScoreText.setText(final_user.getInt("all_rank")+"");
            meJoinnumText.setText(final_user.getInt("all_class")+"");

        }else {
            setNull();
        }
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void logic() {
        if (final_user != null){
            meName.setText(final_user.getUsername().toString());
            meMajor.setText(final_user.getString("school")+" "+final_user.get("major"));
            meScoreText.setText(final_user.getInt("all_rank")+"");
            meJoinnumText.setText(final_user.getInt("all_class")+"");

        }else {
            setNull();
        }

    }

    private void setNull() {
        meName.setText("请先登录");
        meMajor.setText("");
        meScoreText.setText("");
        meJoinnumText.setText("");
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
        if (final_user != null){
            AVUser.logOut();// 清除缓存用户对象
            final_user = null;
            Log.i(TAG, "onLogoutBtnClicked: "+AVUser.getCurrentUser());
        }
        setNull();
    }
}
