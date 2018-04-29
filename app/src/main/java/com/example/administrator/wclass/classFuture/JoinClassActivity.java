package com.example.administrator.wclass.classFuture;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinClassActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.join_class)
    Button joinClass;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_join_class;
    }

    @OnClick(R.id.join_class)
    public void onViewClicked() {
    }
}
