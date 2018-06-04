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
import com.example.administrator.wclass.data.bean.User;

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
        discuss_id = getIntent().getExtras().getString("random_number");
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
                                    discussGetScore.setText(avObject.getString("score").toString()+" 经验");
                                    getDiscussText.setText(avObject.getString("description").toString());
                                    Map<String,String>map = (Map<String, String>) avObject.get("member_discuss");
                                    if (map == null){
                                        map = new HashMap<>();
                                    }
                                    final List<HashMap<String,String>>mapList = new ArrayList<>();
                                    for (Map.Entry<String,String> entry: map.entrySet()){
                                        HashMap<String,String>map1 = new HashMap<>();
                                        map1.put("text1",entry.getKey());
                                        map1.put("text2",entry.getValue());
                                        mapList.add(map1);
                                    }

                                    avObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            SimpleAdapter adapter = new SimpleAdapter(activity, mapList,android.R.layout.simple_list_item_2,
                                                    new String[]{"text1","text2"},new int[]{android.R.id.text1,android.R.id.text2});
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
        if (!getDiscussEdit.getText().toString().equals("")) {
            final AVQuery<AVObject> discussGet = new AVQuery<>("Discuss");
            discussGet.getInBackground(discuss_id, new GetCallback<AVObject>() {
                @Override
                public void done(final AVObject avObject, AVException e) {
                    if (e == null) {
                        if (avObject != null) {
                            Map<String, String> map = (Map<String, String>) avObject.get("member_discuss");
                            if (map == null) {
                                map = new HashMap<>();
                            }
                            Date day = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            System.out.println(df.format(day));
                            String dis = getDiscussEdit.getText().toString();
                            getDiscussEdit.setText("");
                            map.put(AVUser.getCurrentUser().getUsername() + " " + final_user.get("st_number") +"       time: "+ df.format(day),dis);
                            final List<HashMap<String, String>> mapList = new ArrayList<>();
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                HashMap<String, String> map1 = new HashMap<>();
                                map1.put("text1", entry.getKey());
                                map1.put("text2", entry.getValue());
                                mapList.add(map1);
                            }
                            avObject.put("member_discuss", map);
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        Log.i(TAG, "done: ?????????????????????");
                                        getDiscussEdit.setText("");
                                        SimpleAdapter adapter = new SimpleAdapter(activity, mapList, android.R.layout.simple_list_item_2,
                                                new String[]{"text1", "text2"}, new int[]{android.R.id.text1, android.R.id.text2});
                                        getDiscussRecycler.setAdapter(adapter);
                                        Toast.makeText(GetDiscussActivity.this, "讨论成功", Toast.LENGTH_SHORT).show();
                                        AVQuery<AVObject>avQuery = new AVQuery<>("ClassBean");

                                        avQuery.getInBackground(avObject.getString("classbean_id"), new GetCallback<AVObject>() {
                                            @Override
                                            public void done(AVObject avObject2, AVException e) {
                                                if (e == null ){

                                                    Map<String,Integer>map1 = (Map<String, Integer>) avObject2.get("class_user_rank");
                                                    Log.i(TAG, "done: "+map1.size());
                                                    if (map1.get(AVUser.getCurrentUser().getObjectId()) != null){

                                                        int rank = map1.get(AVUser.getCurrentUser().getObjectId());

                                                        final int rankthis = new Integer((String) avObject.get("score"));
                                                        rank += rankthis;
                                                        map1.put(AVUser.getCurrentUser().getObjectId(),rank);

                                                        avObject2.put("class_user_rank",map1);
                                                        AVQuery<AVUser> avUserAVQuery = new AVQuery<>("_User");
                                                        avUserAVQuery.getInBackground(final_user.getObjectId(), new GetCallback<AVUser>() {
                                                            @Override
                                                            public void done(AVUser avUser, AVException e) {
                                                                if (e == null){

                                                                    int rank = avUser.getInt("all_rank");
                                                                    rank =  rank + rankthis;
                                                                    Log.i(TAG, "done: "+rank);
                                                                    avUser.put("all_rank",rank);
                                                                    avUser.saveInBackground(new SaveCallback() {
                                                                        @Override
                                                                        public void done(AVException e) {
                                                                            Log.i(TAG, "done: all score update");
                                                                        }
                                                                    });
                                                                }else {
                                                                    Log.e(TAG, "done: failde" );
                                                                }
                                                            }
                                                        });
                                                        avObject2.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                                if (e == null){
                                                                    Log.i(TAG, "done: success update score");
                                                                }
                                                            }
                                                        });
                                                    }
                                                }else {
                                                    Log.e(TAG, "done: "+e.getMessage() );
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(GetDiscussActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "done: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }else {
            Toast.makeText(activity, "评论不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
