package com.example.administrator.wclass.classFuture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.OnClickerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/29.
 */

public class ClassFragmentAdapter extends RecyclerView.Adapter<ClassFragmentAdapter.ViewHolder> {
    private Context context;
    private List<String> classBeanList;
    private OnClickerListener onClickerListener;

    public ClassFragmentAdapter(Context context,OnClickerListener onClickerListener,List<String>classBeanList) {
        this.context = context;
        this.onClickerListener = onClickerListener;
        this.classBeanList = classBeanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_recycler_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        AVQuery<AVObject> query = new AVQuery<>("ClassBean");
        query.whereEqualTo("class_random_number",classBeanList.get(position));
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null){
                    if (avObject != null){
                        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
                        avQuery.getInBackground(avObject.getString("class_owner_user"), new GetCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                holder.classclass.setText(avObject.getString("class_class"));
                                holder.className.setText(avObject.getString("class_name"));
                                holder.classOwner.setText("开课教师： "+avUser.getUsername());
                                if (avUser.getUsername().equals(AVUser.getCurrentUser().getUsername())){
                                    holder.classRandomNumber.setText(avObject.getString("class_random_number"));
                                }
                            }
                        });

                    }else {
                        Toast.makeText(context, "房间不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        if (onClickerListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickerListener.click(holder.getLayoutPosition(),holder.itemView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return classBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView classImage;
        TextView classRandomNumber;
        TextView classclass;
        TextView className;
        TextView classOwner;

        public ViewHolder(View itemView) {
            super(itemView);
            classImage = itemView.findViewById(R.id.class_avator_item);
            classRandomNumber = itemView.findViewById(R.id.class_random_number_item);
            classclass = itemView.findViewById(R.id.class_class_item);
            className = itemView.findViewById(R.id.class_name_item);
            classOwner = itemView.findViewById(R.id.class_owner_name);
        }

    }

}
