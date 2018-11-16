package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyPointsBean;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPointsAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<MyPointsBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyPointsAdapter(Context mContext, List<MyPointsBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mypoints, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvTime.setText(TimeUtil.getWholeTime2(mDatas.get(position).getCreateTime()));
        ((MyViewHolder) holder).tvPointstitle.setText(mDatas.get(position).getTrackContent());
        String num=mDatas.get(position).getPointnum().substring(1);
        if (mDatas.get(position).getTrackType()==3){
            ((MyViewHolder) holder).tvPointsnum.setTextColor(Color.parseColor("#f87d28"));
        }else {
            ((MyViewHolder) holder).tvPointsnum.setTextColor(Color.parseColor("#666666"));
        }
        ((MyViewHolder) holder).tvPointsnum.setText(mDatas.get(position).getPointnum());
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
        @BindView(R.id.tv_pointstitle)
        TextView tvPointstitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_pointsnum)
        TextView tvPointsnum;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}