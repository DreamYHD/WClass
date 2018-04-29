package com.example.administrator.wclass.classFuture;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateClassActivity extends BaseActivity {

    @BindView(R.id.create_image)
    ImageView createImage;
    @BindView(R.id.class_name_edit)
    EditText classNameEdit;
    @BindView(R.id.class_major_edit)
    EditText classMajorEdit;
    @BindView(R.id.create_class_btn)
    Button createClassBtn;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {

    }
    @Override
    protected int getLayoutView() {
        return R.layout.activity_create_class;
    }
    //换图标
    @OnClick(R.id.create_image)
    public void onCreateImageClicked() {
    }
    //创建
    @OnClick(R.id.create_class_btn)
    public void onCreateClassBtnClicked() {
    }
}
