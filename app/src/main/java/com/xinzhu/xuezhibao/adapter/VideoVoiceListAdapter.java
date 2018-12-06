package com.xinzhu.xuezhibao.adapter;

import android.animation.Animator;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.zou.fastlibrary.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class VideoVoiceListAdapter extends BaseQuickAdapter<VideoVoiceBean,VideoVoiceListAdapter.MyViewHolder> {
    protected Context mContext;
    protected List<VideoVoiceBean> mDatas;
    int TYPE = 0; //标记是否视频课程列表
    public VideoVoiceListAdapter(WeakReference<Context> mContext,@NonNull List<VideoVoiceBean> mDatas) {
        super(R.layout.item_list,mDatas);
        this.mContext = mContext.get();
        this.mDatas = mDatas;
    }

    public VideoVoiceListAdapter(Context mContext, @NonNull List<VideoVoiceBean> mDatas, int TYPE) {
        super(R.layout.item_list,mDatas);
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.TYPE = TYPE;
    }

    @Override
    protected void convert(MyViewHolder helper, VideoVoiceBean item) {
        ((MyViewHolder) helper).tvItemTitle.setText(item.getVideoTitle());
        ((MyViewHolder) helper).tvDianzan.setText(item.getVidelLike());
        ((MyViewHolder) helper).tvItemTime.setText(TimeUtil.getWholeTime2(item.getCreateTime()));
        ((MyViewHolder) helper).tv_readnum.setText(item.getVideoLook());
        if (null == item.getVideoPicture()) {
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
            Glide.with(mContext).load(item.getVideoUrl()).apply(requestOptions).into(((MyViewHolder) helper).simpleDraweeView);
        } else {
            Glide.with(mContext).load(item.getVideoPicture()).into(((MyViewHolder) helper).simpleDraweeView);
        }
        if (TYPE == 2) {
            ((MyViewHolder) helper).tvTeacher.setVisibility(View.VISIBLE);
            ((MyViewHolder) helper).tvTeacher.setText("主讲："+item.getVideoTeacher());
            ((MyViewHolder) helper).tvItemTitle.setMaxLines(1);
        }
    }
    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        anim.setStartDelay(index * 150);
    }


    class MyViewHolder extends BaseViewHolder {
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