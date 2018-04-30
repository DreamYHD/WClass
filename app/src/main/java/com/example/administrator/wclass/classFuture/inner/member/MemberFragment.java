package com.example.administrator.wclass.classFuture.inner.member;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;
import com.example.administrator.wclass.classFuture.ClassFragmentAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends BaseFragment {

    @BindView(R.id.appbar_title_text)
    TextView appbarTitleText;
    @BindView(R.id.rank)
    TextView rank;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.class_member_recycler)
    RecyclerView classMemberRecycler;
    @BindView(R.id.sum_member_text)
    TextView sumMemberText;
    @BindView(R.id.qiandao_btn)
    FloatingActionButton qiandaoBtn;
    private MemberAdapter classFragmentAdapter;

    public static MemberFragment getInstance() {
        return new MemberFragment();
    }
    @Override
    protected void logic() {

    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {
        classFragmentAdapter = new MemberAdapter(getContext(), new OnClickerListener() {
            @Override
            public void click(int position, View view) {

            }
        });
        classMemberRecycler.setAdapter(classFragmentAdapter);
        classMemberRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_member_layout;
    }

    @OnClick(R.id.qiandao_btn)
    public void onViewClicked() {
    }
}
