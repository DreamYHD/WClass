package com.example.administrator.wclass.classFuture.inner.doSome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignedActivity extends BaseActivity {

    @BindView(R.id.key_edit)
    EditText keyEdit;
    @BindView(R.id.start_signed_btn)
    Button startSignedBtn;
    @BindView(R.id.signed_btn)
    Button signedBtn;
    @BindView(R.id.end_signed_btn)
    Button endSignedBtn;
    @BindView(R.id.signed_no_text)
    TextView signedNoText;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_signed;
    }

    @OnClick(R.id.start_signed_btn)
    public void onStartSignedBtnClicked() {
    }

    @OnClick(R.id.signed_btn)
    public void onSignedBtnClicked() {
    }

    @OnClick(R.id.end_signed_btn)
    public void onEndSignedBtnClicked() {
    }
}
