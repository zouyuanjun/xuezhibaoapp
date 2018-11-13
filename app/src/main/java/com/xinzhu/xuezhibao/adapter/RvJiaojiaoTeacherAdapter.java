package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.TeacherBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvJiaojiaoTeacherAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<TeacherBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RvJiaojiaoTeacherAdapter(Context mContext, List<TeacherBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jiajiaoteacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvName.setText(mDatas.get(position).getRealName());
        ((ViewHolder) holder).tvCoursenema.setText(mDatas.get(position).getCurriculumTitle());
        ((ViewHolder) holder).simpleDraweeView2.setImageURI(mDatas.get(position).getHeadPortraitUrl());
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
        @BindView(R.id.simpleDraweeView2)
        SimpleDraweeView simpleDraweeView2;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_coursenema)
        TextView tvCoursenema;
        @BindView(R.id.im_talk)
        ImageView imTalk;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}