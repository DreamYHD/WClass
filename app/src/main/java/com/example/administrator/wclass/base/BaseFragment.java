package com.example.administrator.wclass.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.wclass.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/29.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(getResourcesLayout(), container, false);
        unbinder=ButterKnife.bind(this,view);
        init(view,savedInstanceState);
        logic();
        return view;
    }

    protected abstract void logic();

    protected abstract void init(View mView, Bundle mSavedInstanceState);
    protected abstract int getResourcesLayout();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+getResourcesLayout());

    }
}
