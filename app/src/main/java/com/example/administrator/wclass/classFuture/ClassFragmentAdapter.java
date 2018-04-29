package com.example.administrator.wclass.classFuture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.data.bean.ClassBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/29.
 */

public class ClassFragmentAdapter extends RecyclerView.Adapter<ClassFragmentAdapter.ViewHolder> {
    private Context context;
    private List<ClassBean> classBeanList;
    private OnClickerListener onClickerListener;

    public ClassFragmentAdapter(Context context,OnClickerListener onClickerListener) {
        this.context = context;
        this.onClickerListener = onClickerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    public interface OnClickerListener {
        void click(int position, View view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
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
        return 0;
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_image)
        ImageView classImage;
        @BindView(R.id.class_id_number)
        TextView classIdNumber;
        @BindView(R.id.class_number)
        TextView classNumber;
        @BindView(R.id.class_name)
        TextView className;
        @BindView(R.id.class_user_name)
        TextView classUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
