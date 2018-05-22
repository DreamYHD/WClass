package com.example.administrator.wclass.classFuture.inner.doSome;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final String TAG = "DoFragment";
    @BindView(R.id.do_title_text)
    TextView doTitleText;
    @BindView(R.id.do_recycler_view)
    RecyclerView doRecyclerView;
    @BindView(R.id.do_fbtn)
    RapidFloatingActionButton doFbtn;
    @BindView(R.id.do_fbtn_layout)
    RapidFloatingActionLayout doFbtnLayout;
    private RapidFloatingActionHelper rfabHelper;
    private DoAdapter adapter;
    private static String random_number;

    private List<String>discuss_list =  new ArrayList<>();

    public static DoFragment getInstance(String class_random_number) {
        random_number = class_random_number;
        return new DoFragment();
    }

    @Override
    protected void logic() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("发起答题")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("发起讨论")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("发起签到")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(2)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getContext(),
                doFbtnLayout,
                doFbtn,
                rfaContent
        ).build();
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);


    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {
    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_do;
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);
    }

    private void startActivityByPosition(int position) {
        if (position == 2){
           startActivityTo(SignedActivity.class,random_number );
        }
        if (position == 1){
            startActivityTo(SendDiscussActivity.class,random_number);
        }
        if (position == 0){
        }
    }


    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 获取房间信息成功");
                    if (avObject != null) {
                        //获取课堂的管理者
                        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
                        avQuery.getInBackground(avObject.getString("class_owner_user"), new GetCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                //判断管理者和当前用户是否为同一人
                                if (avUser.getUsername().equals(AVUser.getCurrentUser().getUsername())) {
                                    doFbtn.setVisibility(View.VISIBLE);
                                } else {
                                    doFbtn.setVisibility(View.GONE);
                                }
                            }
                        });
                        discuss_list = avObject.getList("discuss_arr");
                        if (discuss_list == null){
                            discuss_list = new ArrayList<>();
                        }
                        doTitleText.setText(avObject.getString("class_name"));
                        doRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new DoAdapter(getContext(), new OnClickerListener() {
                            @Override
                            public void click(int position, View view) {
                                startActivityTo(GetDiscussActivity.class,discuss_list.get(position));
                            }
                        },discuss_list);
                        doRecyclerView.setAdapter(adapter);


                    } else {
                        Toast.makeText(getContext(), "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
