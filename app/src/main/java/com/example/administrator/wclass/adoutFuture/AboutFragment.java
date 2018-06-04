package com.example.administrator.wclass.adoutFuture;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;
import com.example.administrator.wclass.classFuture.ClassFragmentAdapter;
import com.example.administrator.wclass.classFuture.inner.ClassActivity;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.logout_btn)
    Button logoutBtn;

    public static AboutFragment getInstance() {
        // Required empty public constructor
        return new AboutFragment();
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        Log.i(TAG, "onStart: success");
        final_user = AVUser.getCurrentUser();
        if (final_user != null){
            
            meName.setText(final_user.getUsername().toString());
            meMajor.setText(final_user.getString("school")+" "+final_user.get("major"));

            final int[] cr = {0};
            final int[] jo = {0};
            AVQuery<AVUser> avUserAVQuery = new AVQuery<>("_User");
            avUserAVQuery.getInBackground(final_user.getObjectId(), new GetCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null){
                        meScoreText.setText(avUser.get("all_rank")+"");
                        Log.i(TAG, "onStart: score =  "+avUser.get("all_rank"));
                        List<String> list_create = avUser.getList("create_wclass");
                        List<String> list_join = avUser.getList("join_wclass");
                        if (list_create != null){
                            cr[0] = list_create.size();
                            Log.i(TAG, "done: "+list_create.size()+" "+cr[0]);
                        }
                        if (list_join != null){
                            jo[0] = list_join.size();
                            Log.i(TAG, "done: "+list_join.size()+" "+jo[0]);
                        }
                        meJoinnumText.setText(cr[0] + jo[0] + "");
                    }
                }
            });
        }else {
            setNull();
        }
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void logic() {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: onresume");
    }

    private void setNull() {
        meName.setText("请先登录");
        meMajor.setText("");
        meScoreText.setText("");
        meJoinnumText.setText("");
    }
    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {}

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
