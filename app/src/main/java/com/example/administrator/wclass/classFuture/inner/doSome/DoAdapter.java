package com.example.administrator.wclass.classFuture.inner.doSome;

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

public class DoAdapter extends RecyclerView.Adapter<DoAdapter.ViewHolder> {
    private Context context;
    private OnClickerListener onClickerListener;

    public DoAdapter(Context context, OnClickerListener onClickerListener) {
        this.context = context;
        this.onClickerListener = onClickerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.do_recycler_item, parent, false);
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
        return 5;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.do_item_image)
        ImageView doItemImage;
        @BindView(R.id.do_item_joinnum)
        TextView doItemJoinnum;
        @BindView(R.id.do_item_title)
        TextView doItemTitle;
        @BindView(R.id.do_item_type)
        TextView doItemType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
