package com.example.administrator.wclass.findFuture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {


    public static FindFragment getInstance() {
        // Required empty public constructor
        return new FindFragment();
    }


    @Override
    protected void logic() {

    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {

    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_find;
    }
}
