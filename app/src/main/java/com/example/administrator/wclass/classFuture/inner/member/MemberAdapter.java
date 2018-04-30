package com.example.administrator.wclass.classFuture.inner.member;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.OnClickerListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/30.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private Context context;
    private OnClickerListener onClickerListener;

    public MemberAdapter(Context context, OnClickerListener onClickerListener) {
        this.context = context;
        this.onClickerListener = onClickerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_recycler_item, parent, false);
        return new ViewHolder(view);
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
        return 12;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.member_item_rank)
        TextView memberItemRank;
        @BindView(R.id.member_item_avator)
        ImageView memberItemAvator;
        @BindView(R.id.member_item_all_score)
        TextView memberItemAllScore;
        @BindView(R.id.member_item_name)
        TextView memberItemName;
        @BindView(R.id.member_item_number)
        TextView memberItemNumber;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
