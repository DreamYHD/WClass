package com.example.administrator.wclass.classFuture.inner.show;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;
import com.example.administrator.wclass.classFuture.inner.doSome.DoAdapter;
import com.example.administrator.wclass.classFuture.inner.doSome.GetDiscussActivity;
import com.example.administrator.wclass.classFuture.inner.member.MemberActivity;
import com.example.administrator.wclass.classFuture.inner.member.MemberAdapter;
import com.example.administrator.wclass.utils.MapSortUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends BaseFragment {


    private static final String TAG = "ShowFragment";
    @BindView(R.id.back_from_show_image)
    ImageView backFromShowImage;
    @BindView(R.id.show_title_text)
    TextView showTitleText;
    @BindView(R.id.show_image)
    ImageView showImage;
    @BindView(R.id.show_class_number_text)
    TextView showClassNumberText;
    @BindView(R.id.show_name_text)
    TextView showNameText;
    @BindView(R.id.show_description_text)
    TextView showDescriptionText;
    @BindView(R.id.show_bankehao_text)
    TextView showBankehaoText;
    @BindView(R.id.show_longtime_text)
    TextView showLongtimeText;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    private static String random_number;
    @BindView(R.id.end_class_btn)
    Button endClassBtn;

    public static ShowFragment getInstance(String class_random_number) {
        // Required empty public constructor
        random_number = class_random_number;
        return new ShowFragment();
    }


    @Override
    protected void logic() {

        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 获取房间信息成功");
                    if (avObject != null) {
                        //获取课堂的管理者
                        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
                        avQuery.getInBackground(avObject.getString("class_owner_user"), new GetCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                //判断管理者和当前用户是否为同一人
                                showClassNumberText.setText(avObject.getString("class_class"));
                                showBankehaoText.setText(avObject.getString("class_random_number"));
                                showLongtimeText.setText(avObject.getString("class_duration"));
                                showDescriptionText.setText(avUser.getUsername() + " " + avUser.getString("school") + " " + avUser.getString("major"));
                                showNameText.setText(avObject.getString("class_name"));
                                showTitleText.setText(avObject.getString("class_name"));
                                checkbox.setChecked(avObject.getBoolean("canjoin"));
                                if (avUser.getUsername().equals(AVUser.getCurrentUser().getUsername())) {
                                    checkbox.setVisibility(View.VISIBLE);
                                    endClassBtn.setText("结束班课");
                                } else {
                                    checkbox.setVisibility(View.VISIBLE);
                                    checkbox.setClickable(false);
                                    endClassBtn.setText("退出班课");
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setIsChecked(true);
                }else {
                    setIsChecked(false);
                }
            }
        });
    }

    private void setIsChecked(final boolean b) {
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number", random_number);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: 获取房间信息成功");
                    if (avObject != null) {
                        avObject.put("canjoin",b);
                        Log.i(TAG, "done: boolean = " + b);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if ( e == null){
                                    Log.i(TAG, "done: success change check");
                                }else {
                                    Log.e(TAG, "done: "+e.getMessage() );
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {

    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_show;
    }


    @OnClick(R.id.back_from_show_image)
    public void onViewClicked() {
        getActivity().finish();
    }

    @OnClick(R.id.end_class_btn)
    public void onViewClicked2() {
        if (endClassBtn.getText().equals("结束班课")) {
            // 执行 CQL 语句实现删除一个 Todo 对象
            //从自己创建列表中删除
            AVQuery<AVUser> avUserAVQuery = new AVQuery<>("_User");
            avUserAVQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    List<String> list = avUser.getList("create_wclass");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (list.contains(random_number)) {
                        list.remove(random_number);
                    }
                    avUser.put("create_wclass", list);
                    avUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                AVQuery<AVObject> query = new AVQuery<>("ClassBean");
                                query.whereEqualTo("class_random_number", random_number);
                                query.getFirstInBackground(new GetCallback<AVObject>() {
                                    @Override
                                    public void done(final AVObject avObject, AVException e) {
                                        if (e == null) {
                                            Log.i(TAG, "done: 获取房间信息成功");
                                            if (avObject != null) {
                                                avObject.put("isend",true);
                                                avObject.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(AVException e) {
                                                        Toast.makeText(getContext(), "结束成功", Toast.LENGTH_SHORT).show();
                                                        getActivity().finish();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getActivity(), "房间不存在", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });

        } else {//退出
            AVQuery<AVObject> query = new AVQuery<>("ClassBean");
            query.whereEqualTo("class_random_number", random_number);
            query.getFirstInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(final AVObject avObject, AVException e) {
                    if (e == null) {
                        List<String> list_user = (List<String>) avObject.get("user_list");
                        if (list_user != null) {
                            if (list_user.contains(AVUser.getCurrentUser().getObjectId())) {
                                list_user.remove(AVUser.getCurrentUser().getObjectId());
                            }
                        }
                        Map<String, Integer> map = avObject.getMap("class_user_rank");
                        if (map != null) {
                            if (map.containsKey(AVUser.getCurrentUser().getObjectId())) {
                                map.remove(AVUser.getCurrentUser().getObjectId());
                            }
                        }
                        avObject.put("user_list", list_user);
                        avObject.put("class_user_rank", map);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                final AVQuery<AVUser> avQuery_user = new AVQuery<>("_User");
                                avQuery_user.getInBackground(final_user.getObjectId(), new GetCallback<AVUser>() {
                                    @Override
                                    public void done(final AVUser avUser, AVException e) {

                                        List<String> list = avUser.getList("join_wclass");
                                        if (list == null){
                                            Log.i(TAG, "done: list is null");
                                            list = new ArrayList<>();
                                        }
                                        if (list.contains(random_number)) {
                                            list.remove(random_number);
                                        }
                                        avUser.put("join_wclass", list);
                                        avUser.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                Toast.makeText(getActivity(), "结束成功", Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                });
                            }
                        });

                    }
                }
            });
        }
    }
}
