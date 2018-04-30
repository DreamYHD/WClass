package com.example.administrator.wclass.classFuture.inner.show;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wclass.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {


    public static ShowFragment getInstance() {
        // Required empty public constructor
        return new ShowFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

}
