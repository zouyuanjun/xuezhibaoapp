package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.zou.fastlibrary.utils.StringUtil;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class RvJiaojiaoCourseAdapter extends RecyclerView.Adapter {
    protected WeakReference<Context> mContext;
    protected List<CourseBean> mDatas;
    private OnItemClickListener onItemClickListener;
    int VIEWTYPE1 = 1;
    int VIEWTYPE2 = 2;

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
        View view;
        if (viewType == VIEWTYPE1) {
            view = LayoutInflater.from(mContext.get()).inflate(R.layout.item_jiajiao_course, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext.get()).inflate(R.layout.item_jiating_course, parent, false);
            return new ViewHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).tvItemTitle.setText(mDatas.get(position).getCurriculumTitle());
            ((ViewHolder) holder).tvTeacher.setText("主讲："+mDatas.get(position).getSpeakerTeacher());
            ((ViewHolder) holder).tvAll.setText("共"+mDatas.get(position).getSumHour() + "节");
            ((ViewHolder) holder).tvReadnum.setText(mDatas.get(position).getCurriculumApply() + "");
            ((ViewHolder) holder).tvAlready.setText("/学习" + mDatas.get(position).getConsumeHour()+"节");
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
                        onItemClickListener.feedBackClick(view, position);
                    }
                });
            }
        }else if (holder instanceof ViewHolder2){
            ((ViewHolder2) holder).tvItemTitle.setText(mDatas.get(position).getCurriculumTitle());
            ((ViewHolder2) holder).tvTeacher.setText("主讲："+mDatas.get(position).getSpeakerTeacher());
            ((ViewHolder2) holder).tvReadnum.setText(mDatas.get(position).getCurriculumApply()+"");

            if (!StringUtil.isEmpty(mDatas.get(position).getDictionaryName())){
                ((ViewHolder2) holder).tvClass.setVisibility(View.VISIBLE);
                ((ViewHolder2) holder).tvClass.setText(mDatas.get(position).getDictionaryName());
            }
                Glide.with(mContext.get()).load(mDatas.get(position).getCurriculumPicture()).into(((ViewHolder2) holder).simpleDraweeView);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getIsApply()==1){
            return VIEWTYPE1;
        }else {
            return VIEWTYPE2;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void feedBackClick(View view, int position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
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

   static class ViewHolder2 extends RecyclerView.ViewHolder{
        @BindView(R.id.simpleDraweeView)
        ImageView simpleDraweeView;
        @BindView(R.id.simpleDraweeView2)
        ImageView simpleDraweeView2;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.im_watch_ioc)
        ImageView imWatchIoc;
        @BindView(R.id.tv_readnum)
        TextView tvReadnum;
        @BindView(R.id.tv_class)
        TextView tvClass;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}