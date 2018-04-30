package com.example.administrator.wclass.classFuture.inner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;
import com.example.administrator.wclass.classFuture.inner.doSome.DoFragment;
import com.example.administrator.wclass.classFuture.inner.member.MemberFragment;
import com.example.administrator.wclass.classFuture.inner.show.ShowFragment;
import com.example.administrator.wclass.utils.ActivityUtils;
import com.example.administrator.wclass.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class ClassActivity extends BaseActivity {

    private static final String TAG = "ClassActivity";
    @BindView(R.id.class_frame)
    FrameLayout classFrame;
    @BindView(R.id.class_bottom_menu)
    BottomNavigationView classBottomMenu;


    @Override
    protected void logicActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            Log.i(TAG, "ClassActivity: "+savedInstanceState.getInt("bottom_id_class"));
            showFragment(savedInstanceState.getInt("bottom_id_class"));
        }else {
            ActivityUtils.replaceFragmentToActivity(fragmentManager, MemberFragment.getInstance(),R.id.class_frame);
        }
        BottomNavigationViewHelper.disableShiftMode(classBottomMenu);
        classBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                return true;
            }
        });
    }

    /**
     * 根据id显示相应的页面
     * @param menu_id
     */
    private void showFragment(int menu_id) {
        switch (menu_id){
            case R.id.bottom_person:
                ActivityUtils.replaceFragmentToActivity(fragmentManager,MemberFragment.getInstance(),R.id.class_frame);
                break;
            case R.id.bottom_do:
                ActivityUtils.replaceFragmentToActivity(fragmentManager, DoFragment.getInstance(),R.id.class_frame);
                break;
            case R.id.bottom_show:
                ActivityUtils.replaceFragmentToActivity(fragmentManager, ShowFragment.getInstance(),R.id.class_frame);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bottom_id_class",classBottomMenu.getSelectedItemId());
    }


    @Override
    protected int getLayoutView() {
        return R.layout.activity_clas;
    }

}
