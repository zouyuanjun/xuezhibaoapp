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
import com.xinzhu.xuezhibao.bean.JiaojiaoCourseBean;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class RvJiaojiaoCourseAdapter extends RecyclerView.Adapter {
    protected WeakReference<Context> mContext;
    protected List<JiaojiaoCourseBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RvJiaojiaoCourseAdapter(WeakReference<Context> mContext, List<JiaojiaoCourseBean> mDatas) {
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
        ((ViewHolder) holder).tvItemTitle.setText(mDatas.get(position).getTitle());
        ((ViewHolder) holder).tvTeacher.setText(mDatas.get(position).getTeacher());
        ((ViewHolder) holder).tvAll.setText(mDatas.get(position).getAllclass() + "/");
        Log.d(mDatas.get(position).getAllclass() + "/");
        ((ViewHolder) holder).tvReadnum.setText(mDatas.get(position).getReadnum());
        ((ViewHolder) holder).tvAlready.setText(mDatas.get(position).getAlreadyclass());
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
                    messageDigest.update((mContext.get().getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(mContext.get()).load(mDatas.get(position).getImurl()).apply(requestOptions).into(((ViewHolder) holder).simpleDraweeView);

        // ((MyViewHolder) holder).simpleDraweeView.setImageBitmap(ImageUtils.createVideoThumbnail(mDatas.get(position).getArticlePicture(),MediaStore.Images.Thumbnails.MINI_KIND));

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
        @BindView(R.id.tv_feedback)
        TextView tvFeedback;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}