package com.example.administrator.wclass.classFuture.inner.doSome;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final String TAG = "DoFragment";
    @BindView(R.id.do_title_text)
    TextView doTitleText;
    @BindView(R.id.do_tab)
    TabLayout doTab;
    @BindView(R.id.do_recycler_view)
    RecyclerView doRecyclerView;
    @BindView(R.id.do_fbtn)
    RapidFloatingActionButton doFbtn;
    @BindView(R.id.do_fbtn_layout)
    RapidFloatingActionLayout doFbtnLayout;
    Unbinder unbinder;
    private RapidFloatingActionHelper rfabHelper;
    private DoAdapter adapter;

    public static DoFragment getInstance() {
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
        for (int i = 0; i < doTab.getTabCount(); i++) {
            TabLayout.Tab tab = doTab.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        Log.i(TAG, "onClick: "+position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {
        doRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DoAdapter(getContext(), new OnClickerListener() {
            @Override
            public void click(int position, View view) {

            }
        });
        doRecyclerView.setAdapter(adapter);

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
            Intent intent = new Intent(getContext(),SignedActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        startActivityByPosition(position);


    }
}
