package com.example.administrator.wclass.classFuture.inner.doSome;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;
import com.example.administrator.wclass.base.OnClickerListener;
import com.example.administrator.wclass.classFuture.inner.member.MemberAdapter;
import com.example.administrator.wclass.utils.MapSortUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignedActivity extends BaseActivity {

    private static final String TAG = "SignedActivity";
    @BindView(R.id.key_edit)
    EditText keyEdit;
    @BindView(R.id.start_signed_btn)
    Button startSignedBtn;
    @BindView(R.id.end_signed_btn)
    Button endSignedBtn;
    @BindView(R.id.signed_no_text)
    TextView signedNoText;
    private String random_number;
    private String key;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {
        random_number = getIntent().getExtras().getString("random_number");

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_signed;
    }

    @OnClick(R.id.start_signed_btn)
    public void onStartSignedBtnClicked() {
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 获取房间信息成功");
                    if (avObject != null) {
                        //获取课堂的管理者
                        key = keyEdit.getText().toString().trim();
                        avObject.put("class_signin_number",key);
                        Log.i(TAG, "done: "+key);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){
                                    Toast.makeText(SignedActivity.this, "签到成功开始", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(activity, "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    @OnClick(R.id.end_signed_btn)
    public void onEndSignedBtnClicked() {
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 获取房间信息成功");
                    if (avObject != null) {
                        avObject.put("class_signin_number","");
                        Log.i(TAG, "done: "+key);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){
                                    Toast.makeText(SignedActivity.this, "签到成功关闭", Toast.LENGTH_SHORT).show();

                                    // TODO: 2018/5/20 展示没有签到的，把列表更新为null 
                                }
                            }
                        });
                    } else {
                        Toast.makeText(activity, "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
