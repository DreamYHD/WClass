package com.example.administrator.wclass.classFuture;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.classFuture.inner.ClassActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private ClassFragmentAdapter classFragmentAdapter;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.class_fragment_recycler)
    RecyclerView classFragmentRecycler;
    @BindView(R.id.activity_main_rfab)
    RapidFloatingActionButton activityMainRfab;
    @BindView(R.id.activity_main_rfal)
    RapidFloatingActionLayout activityMainRfal;
    private RapidFloatingActionHelper rfabHelper;

    public static ClassFragment getInstance() {
        // Required empty public constructor
        return new ClassFragment();
    }

    @Override
    protected void logic() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("创建微课")
                .setResId(R.drawable.ic_cloud_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("使用微课号加入微课")
                .setResId(R.drawable.ic_cloud_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getContext(),
                activityMainRfal,
                activityMainRfab,
                rfaContent
        ).build();
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);


    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {
        classFragmentRecycler.setLayoutManager(linearLayoutManager);
        classFragmentAdapter = new ClassFragmentAdapter(getContext(), new ClassFragmentAdapter.OnClickerListener() {
            @Override
            public void click(int position, View view) {

            }
        });
        classFragmentRecycler.setAdapter(classFragmentAdapter);
    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_class;
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);
    }

    private void startActivityByPosition(int position) {
        if (position == 0){
            Intent intent = new Intent(getContext(), CreateClassActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(), ClassActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);
    }
}
