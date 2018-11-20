package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvJiaojiaoFeedbackAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<CourseFeedbackBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RvJiaojiaoFeedbackAdapter(Context mContext, List<CourseFeedbackBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_jiajiaofeedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvFeedbacktitle.setText(mDatas.get(position).getContent());
        if (StringUtil.isEmpty(mDatas.get(position).getReplyContent(),true)){
            ((ViewHolder) holder).tvConent.setText("暂无回复");
        }else {
            ((ViewHolder) holder).tvConent.setText(mDatas.get(position).getReplyContent());
        }

        if (mDatas.get(position).getReplyCount()>0){
            ((ViewHolder) holder).tvNum.setText(mDatas.get(position).getReplyCount()+"");
        }else {
           ((ViewHolder) holder).tvNum.setVisibility(View.GONE);
        }
        ((ViewHolder) holder).tvFeedbacktheme.setText("关于课程“"+mDatas.get(position).getCurriculumTitle()+"”的反馈");
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
        @BindView(R.id.textView12)
        TextView textView12;
        @BindView(R.id.tv_feedbacktitle)
        TextView tvFeedbacktitle;
        @BindView(R.id.tv_conent)
        TextView tvConent;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_feedbacktheme)
        TextView tvFeedbacktheme;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}