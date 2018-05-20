package com.example.administrator.wclass.classFuture.inner.member;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberActivity extends BaseActivity {


    private static final String TAG = "MemberActivity";
    @BindView(R.id.member_sigin_back)
    ImageView memberSiginBack;
    @BindView(R.id.member_sigin_edit)
    EditText memberSiginEdit;
    @BindView(R.id.member_sigin_btn)
    Button memberSiginBtn;
    private String random_number;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {
         random_number = getIntent().getExtras().getString("random_number");
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_member;
    }

    @OnClick(R.id.member_sigin_back)
    public void onMemberSiginBackClicked() {
        activity.finish();
    }

    @OnClick(R.id.member_sigin_btn)
    public void onMemberSiginBtnClicked() {
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 签到已经开始");
                    if (avObject != null) {
                        String key = avObject.get("class_signin_number").toString();
                        String member_key = memberSiginEdit.getText().toString().trim();
                        if (key != null && !key.equals("") && key.equals(member_key)){
                            List<String>list = avObject.getList("user_sigin_list");
                            if ( list == null){
                                list = new ArrayList<>();
                            }
                            if (!list.contains(AVUser.getCurrentUser().getObjectId().toString())){
                                list.add(AVUser.getCurrentUser().getObjectId().toString());
                                avObject.put("user_sigin",list);
                                avObject.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if ( e == null){
                                            Toast.makeText(MemberActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                                            Map<String,Integer>map = avObject.getMap("class_user_rank");
                                            int value = map.get(AVUser.getCurrentUser().getObjectId());
                                            value += 2;
                                            map.put(AVUser.getCurrentUser().getObjectId(),value);
                                            avObject.put("class_user_rank",map);
                                            avObject.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    Log.i(TAG, "done: 经验值加上了");
                                                }
                                            });
                                            activity.finish();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(activity, "你已经签到过了", Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(activity, "签到码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, "签到未开始", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
