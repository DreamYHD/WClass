package com.example.administrator.wclass.classFuture;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinClassActivity extends BaseActivity {

    private static final String TAG = "JoinClassActivity";
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
        final String class_random_number = editText.getText().toString().trim();
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number",class_random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null){
                    if (avObject != null){
                        if (avObject.get("class_owner_user").equals(final_user.getObjectId())){
                            Toast.makeText(activity, "你已经是该课堂的管理者", Toast.LENGTH_SHORT).show();
                        }else {
                            final AVQuery<AVUser> avQuery_user = new AVQuery<>("_User");
                            avQuery_user.getInBackground(final_user.getObjectId(), new GetCallback<AVUser>() {
                                @Override
                                public void done(final AVUser avUser, AVException e) {

                                    List<String> list = avUser.getList("join_wclass");
                                    if (list == null){
                                        Log.i(TAG, "done: list is null");
                                        list = new ArrayList<>();
                                    }
                                    if (!list.contains(class_random_number)){
                                        list.add(class_random_number);
                                        avUser.put("join_wclass",list);
                                        avUser.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e == null){
                                                    Toast.makeText(activity, "加入成功,快去学习吧", Toast.LENGTH_SHORT).show();
                                                    activity.finish();
                                                    Log.i(TAG, "done: success update join_wclass");
                                                    List<String>user_list = avObject.getList("user_list");
                                                    Map<String,Integer> user_rank = (Map<String,Integer>) avObject.get("class_user_rank");
                                                    if (user_list == null){
                                                        user_list = new ArrayList<>();
                                                        user_rank = new HashMap<>();
                                                    }
                                                    Log.i(TAG, "done: "+user_list.size());
                                                    user_list.add(final_user.getObjectId());
                                                    user_rank.put(final_user.getObjectId(),0);
                                                    Log.i(TAG, "done: "+user_list.size());
                                                    avObject.put("user_list",user_list);
                                                    avObject.put("class_user_rank",user_rank);
                                                    avObject.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            if ( e == null){
                                                                Log.i(TAG, "done: success add to classbean");
                                                            }else {
                                                                Log.e(TAG, "done: "+e.getMessage() );
                                                            }
                                                        }

                                                    });
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(activity, "你已经加入了该课堂", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            
                            
                        }
                    }else {
                        Toast.makeText(activity, "课堂不存在,请检查课堂号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
