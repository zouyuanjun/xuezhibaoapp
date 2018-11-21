package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseBean;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvJiaojiaoCourseAdapter extends RecyclerView.Adapter {
    protected WeakReference<Context> mContext;
    protected List<CourseBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RvJiaojiaoCourseAdapter(WeakReference<Context> mContext, List<CourseBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.get()).inflate(R.layout.item_jiajiao_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvItemTitle.setText(mDatas.get(position).getCurriculumTitle());
        ((ViewHolder) holder).tvTeacher.setText(mDatas.get(position).getSpeakerTeacher());
        ((ViewHolder) holder).tvAll.setText(mDatas.get(position).getSumHour() + "节");
        ((ViewHolder) holder).tvReadnum.setText(mDatas.get(position).getCurriculumApply()+"");
        ((ViewHolder) holder).tvAlready.setText("/"+mDatas.get(position).getConsumeHour());
        Glide.with(mContext.get()).load(mDatas.get(position).getCurriculumPicture()).into(((ViewHolder) holder).simpleDraweeView);
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
            ((ViewHolder) holder).tvFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.feedBackClick(view,position);
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
        void feedBackClick(View view, int position);
    };

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.simpleDraweeView)
        ImageView simpleDraweeView;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.tv_readnum)
        TextView tvReadnum;
        @BindView(R.id.tv_all)
        TextView tvAll;
        @BindView(R.id.tv_already)
        TextView tvAlready;
        @BindView(R.id.ll_feedback)
        LinearLayout tvFeedback;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}