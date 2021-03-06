package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyjobBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvJiaojiaoTaskAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<MyjobBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RvJiaojiaoTaskAdapter(Context mContext, List<MyjobBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).textView14.setText(mDatas.get(position).getCreateTime());
        if (mDatas.get(position).getState().equals("100")){
            ((ViewHolder) holder).tvStatus.setText("未完成");
        }else {
            ((ViewHolder) holder).tvStatus.setText("已完成");
            ((ViewHolder) holder).tvStatus.setTextColor(Color.parseColor("#12cd8e"));
        }

        ((ViewHolder) holder).tvTasktitle.setText(mDatas.get(position).getJobTitle());
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tasktitle)
        TextView tvTasktitle;
        @BindView(R.id.textView14)
        TextView textView14;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}