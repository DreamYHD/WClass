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

import java.util.ArrayList;
import java.util.List;

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
                                showDescriptionText.setText(avUser.getUsername()+" "+avUser.getString("school")+" "+avUser.getString("major"));
                                showNameText.setText(avObject.getString("class_name"));
                                showTitleText.setText(avObject.getString("class_name"));
                                if (avUser.getUsername().equals(AVUser.getCurrentUser().getUsername())) {
                                    checkbox.setVisibility(View.VISIBLE);
                                    endClassBtn.setText("结束班课");
                                } else {
                                    checkbox.setVisibility(View.GONE);
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
        if (endClassBtn.getText().equals("结束班课")){
            // 执行 CQL 语句实现删除一个 Todo 对象
            AVQuery.doCloudQueryInBackground("delete from ClassBean where class_random_number="+random_number, new CloudQueryCallback<AVCloudQueryResult>() {
                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                    if (e == null){
                        List<String>list = final_user.getList("create_wclass");
                        if (list == null){
                            list = new ArrayList<>();
                        }
                        if (list.contains(random_number)){
                            list.remove(random_number);
                        }
                        final_user.put("create_wclass",list);

                        final_user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){

                                }
                            }
                        });
                        AVQuery<AVUser>avUserAVQuery = new AVQuery<>("_User");
                        avUserAVQuery.findInBackground(new FindCallback<AVUser>() {
                            @Override
                            public void done(List<AVUser> list, AVException e) {
                                for (AVUser user :list){
                                    List<String>list2 = user.getList("join_wclass");
                                    if (list2 == null){
                                        list2 = new ArrayList<>();
                                    }
                                    if (list2.contains(random_number)){
                                        list2.remove(random_number);
                                    }
                                    user.put("join_wclass",list2);
                                    user.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            Toast.makeText(getActivity(), "结束成功", Toast.LENGTH_SHORT).show();
                                            getActivity().finish();
                                        }
                                    });
                                }
                            }
                        });

                    }
                }
            });
        }

    }
}
