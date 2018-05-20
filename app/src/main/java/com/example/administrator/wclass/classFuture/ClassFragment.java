package com.example.administrator.wclass.classFuture;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;
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
    private List list_create = new ArrayList<>();
    private List list_join = new ArrayList<>();
    private List<String>list_all = new ArrayList<>();
    private static final String TAG = "ClassFragment";

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
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("使用微课号加入微课")
                .setResId(R.drawable.ic_add_black_24dp)
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

    //从网络上获取数据更新数据
    private void updateData() {
        if (list_all.size()!= 0){
            list_all.clear();
        }
        if (final_user != null){
            AVQuery<AVUser>avUserAVQuery = new AVQuery<>("_User");
            avUserAVQuery.getInBackground(final_user.getObjectId(), new GetCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null){
                        Log.i(TAG, "done: "+avUser.getUsername()+avUser.get("create_wclass"));
                        list_create = avUser.getList("create_wclass");
                        list_join = avUser.getList("join_wclass");
                        if (list_create != null){
                            list_all.addAll(list_create);
                            Log.i(TAG, "done: "+list_create.size());
                        }
                        if (list_join != null){
                            list_all.addAll(list_join);
                        }
                        linearLayoutManager = new LinearLayoutManager(getActivity());
                        classFragmentRecycler.setLayoutManager(linearLayoutManager);
                        classFragmentAdapter = new ClassFragmentAdapter(getContext(),
                                new OnClickerListener() {
                                    @Override
                                    public void click(int position, View view) {
                                        Intent intent = new Intent(getContext(),ClassActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("class_random_number",list_all.get(position));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                },list_all);
                        Log.i(TAG, "updateData: "+list_all.size());
                        classFragmentRecycler.setAdapter(classFragmentAdapter);
                        classFragmentAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {
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
            Intent intent = new Intent(getContext(), JoinClassActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }
}
