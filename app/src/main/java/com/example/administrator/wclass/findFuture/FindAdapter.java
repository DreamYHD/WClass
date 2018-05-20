package com.example.administrator.wclass.findFuture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.OnClickerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/1.
 */

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {
    private Context context;
    private OnClickerListener onClickerListener;
    private List<String> list = new ArrayList<>();
    private static final String TAG = "FindAdapter";

    public FindAdapter(Context context, OnClickerListener onClickerListener, List<String> list) {
        this.context = context;
        this.onClickerListener = onClickerListener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String title = list.get(position);
        String[]t = title.split("\\..");
        Log.i(TAG, "onBindViewHolder: "+t[0]);
        holder.textView.setText(t[0]);
        if (onClickerListener != null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickerListener.click(holder.getLayoutPosition(),holder.textView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.find_item_text);
        }
    }
}
