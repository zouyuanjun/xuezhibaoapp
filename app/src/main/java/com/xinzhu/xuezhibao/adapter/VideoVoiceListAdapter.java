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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.zou.fastlibrary.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class VideoVoiceListAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<VideoVoiceBean> mDatas;
    private OnItemClickListener onItemClickListener;
    int TYPE = 0; //标记是否视频课程列表

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VideoVoiceListAdapter(WeakReference<Context> mContext, List<VideoVoiceBean> mDatas) {
        this.mContext = mContext.get();
        this.mDatas = mDatas;
    }

    public VideoVoiceListAdapter(Context mContext, List<VideoVoiceBean> mDatas, int TYPE) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvItemTitle.setText(mDatas.get(position).getVideoTitle());
        ((MyViewHolder) holder).tvDianzan.setText(mDatas.get(position).getVidelLike());
        ((MyViewHolder) holder).tvItemTime.setText(TimeUtil.getWholeTime2(mDatas.get(position).getCreateTime()));
        ((MyViewHolder) holder).tv_readnum.setText(mDatas.get(position).getVideoLook());
        if (null == mDatas.get(position).getVideoPicture()) {
            RequestOptions requestOptions = RequestOptions.frameOf(0);
            requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
            requestOptions.transform(new BitmapTransformation() {
                @Override
                protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                    return toTransform;
                }

                @Override
                public void updateDiskCacheKey(MessageDigest messageDigest) {
                    try {
                        messageDigest.update((mContext.getPackageName() + "RotateTransform").getBytes("utf-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Glide.with(mContext).load(mDatas.get(position).getVideoUrl()).apply(requestOptions).into(((MyViewHolder) holder).simpleDraweeView);
        } else {
            Glide.with(mContext).load(mDatas.get(position).getVideoPicture()).into(((MyViewHolder) holder).simpleDraweeView);
        }
        if (TYPE == 2) {
            ((MyViewHolder) holder).tvTeacher.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).tvTeacher.setText("主讲："+mDatas.get(position).getVideoTeacher());
            ((MyViewHolder) holder).tvItemTitle.setMaxLines(1);
        }

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


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simpleDraweeView)
        ImageView simpleDraweeView;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.im_dianzan)
        ImageView imDianzan;
        @BindView(R.id.tv_dianzan)
        TextView tvDianzan;
        @BindView(R.id.im_watch_ioc)
        ImageView imWatchIoc;
        @BindView(R.id.tv_readnum)
        TextView tv_readnum;
        @BindView(R.id.imageView10)
        ImageView imageView10;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}