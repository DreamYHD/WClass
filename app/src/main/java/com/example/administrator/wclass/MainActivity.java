package com.example.administrator.wclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.wclass.adoutFuture.AboutFragment;
import com.example.administrator.wclass.base.BaseActivity;
import com.example.administrator.wclass.classFuture.ClassFragment;
import com.example.administrator.wclass.findFuture.FindFragment;
import com.example.administrator.wclass.utils.ActivityUtils;
import com.example.administrator.wclass.utils.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;
    private static final String TAG = "MainActivity";

    @Override
    protected void logicActivity(Bundle savedInstanceState) {
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
        if (savedInstanceState != null){
            Log.i(TAG, "logicActivity: "+savedInstanceState.getInt("bottom_id"));
            showFragment(savedInstanceState.getInt("bottom_id"));
        }else {
            ActivityUtils.replaceFragmentToActivity(fragmentManager,ClassFragment.getInstance(),R.id.main_frame);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
            case R.id.bottom_menu_class:
                ActivityUtils.replaceFragmentToActivity(fragmentManager,ClassFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_about:
                ActivityUtils.replaceFragmentToActivity(fragmentManager, AboutFragment.getInstance(),R.id.main_frame);
                break;
            case R.id.bottom_menu_find:
                ActivityUtils.replaceFragmentToActivity(fragmentManager, FindFragment.getInstance(),R.id.main_frame);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bottom_id",bottomMenu.getSelectedItemId());
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

}
