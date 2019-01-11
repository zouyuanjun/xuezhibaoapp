package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyTaskBean;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<MyTaskBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TaskListAdapter(WeakReference<Context> mContext, List<MyTaskBean> mDatas) {
        this.mContext = mContext.get();
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mytask, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvTasktitle.setText(mDatas.get(position).getTaskTitle());
        ((MyViewHolder) holder).textView16.setText(mDatas.get(position).getAwardIntegral() + "");
        if (mDatas.get(position).getTaskNumber().equals("999")){
            mDatas.get(position).setTaskNumber("1");
        }
        ((MyViewHolder) holder).tvAllnum.setText("/"+mDatas.get(position).getTaskNumber());
        ((MyViewHolder) holder).tvCompletenum.setText(mDatas.get(position).getCount());
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tasktitle)
        TextView tvTasktitle;
        @BindView(R.id.textView16)
        TextView textView16;
        @BindView(R.id.tv_allnum)
        TextView tvAllnum;
        @BindView(R.id.tv_completenum)
        TextView tvCompletenum;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}