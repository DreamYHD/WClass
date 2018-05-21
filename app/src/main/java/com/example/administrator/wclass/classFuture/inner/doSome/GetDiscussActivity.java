package com.example.administrator.wclass.classFuture.inner.doSome;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetDiscussActivity extends BaseActivity {

    private static final String TAG = "GetDiscussActivity";
    @BindView(R.id.xuanshang_get_bank_image)
    ImageView xuanshangGetBankImage;
    @BindView(R.id.get_discuss_avator)
    ImageView getDiscussAvator;
    @BindView(R.id.discuss_get_name_text)
    TextView discussGetNameText;
    @BindView(R.id.discuss_get_time_text)
    TextView discussGetTimeText;
    @BindView(R.id.discuss_get_score)
    TextView discussGetScore;
    @BindView(R.id.get_discuss_text)
    TextView getDiscussText;
    @BindView(R.id.get_discuss_edit)
    EditText getDiscussEdit;
    @BindView(R.id.get_discuss_image)
    ImageView getDiscussImage;
    @BindView(R.id.get_discuss_recycler)
    ListView getDiscussRecycler;
    private String discuss_id;

    @Override
    protected void logicActivity(Bundle savedInstanceState) {
        final AVQuery<AVObject> discussGet = new AVQuery<>("Discuss");
        discussGet.getInBackground(discuss_id, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    if (avObject != null) {
                        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
                        avQuery.getInBackground(avObject.getString("owner"), new GetCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e == null) {
                                    discussGetNameText.setText(avUser.getUsername().toString());
                                    discussGetTimeText.setText(avObject.getString("time").toString());
                                    discussGetScore.setText(avObject.getString("score").toString());
                                    getDiscussText.setText(avObject.getString("description").toString());
                                    Map<String,String>map = (Map<String, String>) avObject.get("member_discuss");
                                    if (map == null){
                                        map = new HashMap<>();
                                    }
                                    final Map<String, String> finalMap = map;
                                    avObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            SimpleAdapter adapter = new SimpleAdapter(activity, (List<Map<String,String>>) finalMap,android.R.layout.simple_list_item_2,
                                                    new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
                                            getDiscussRecycler.setAdapter(adapter);
                                        }
                                    });
                                } else {
                                    Log.i(TAG, "done: 哼 人家没找到这坏蛋");
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_gwt_discuss;
    }

    @OnClick(R.id.xuanshang_get_bank_image)
    public void onXuanshangGetBankImageClicked() {
        activity.finish();
    }

    @OnClick(R.id.get_discuss_image)
    public void onGetDiscussImageClicked() {
        final AVQuery<AVObject> discussGet = new AVQuery<>("Discuss");
        discussGet.getInBackground(discuss_id, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    if (avObject != null) {
                        Map<String,String>map = (Map<String, String>) avObject.get("member_discuss");
                        if (map == null){
                            map = new HashMap<>();
                        }
                        map.put(final_user.getUsername()+" "+final_user.getString("st_number"),getDiscussEdit.getText().toString());
                        avObject.put("member_discuss",map);
                        final Map<String, String> finalMap = map;
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){
                                    SimpleAdapter adapter = new SimpleAdapter(activity, (List<Map<String,String>>) finalMap,android.R.layout.simple_list_item_2,
                                            new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
                                    getDiscussRecycler.setAdapter(adapter);
                                    Toast.makeText(GetDiscussActivity.this, "讨论成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(GetDiscussActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
