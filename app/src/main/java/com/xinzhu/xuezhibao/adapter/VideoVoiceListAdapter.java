package com.xinzhu.xuezhibao.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.view.View;
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
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;
//视频和音频课程列表adapter
public class VideoVoiceListAdapter extends BaseQuickAdapter<VideoVoiceBean,VideoVoiceListAdapter.MyViewHolder> {
    protected Context mContext;
    protected List<VideoVoiceBean> mDatas;
    int TYPE = 0; //标记是否视频课程列表，2：视频列表，否则音频列表
    int PayVideo=0; //是否是收费视频，决定是否要显示试看标签
    public VideoVoiceListAdapter(WeakReference<Context> mContext,@NonNull List<VideoVoiceBean> mDatas) {
        super(R.layout.item_list,mDatas);
        this.mContext = mContext.get();
        this.mDatas = mDatas;
    }
    public VideoVoiceListAdapter(Context mContext, @NonNull List<VideoVoiceBean> mDatas, int TYPE,int PayVideo) {
        super(R.layout.item_list,mDatas);
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.TYPE = TYPE;
        this.PayVideo=PayVideo;
    }
    @Override
    protected void convert(MyViewHolder helper, VideoVoiceBean item) {
        ((MyViewHolder) helper).tvItemTitle.setText(item.getVideoTitle());
        ((MyViewHolder) helper).tvDianzan.setText(item.getVideoLikeFalse());
        ((MyViewHolder) helper).tvItemTime.setText(TimeUtil.getWholeTime3(item.getCreateTime()));
        ((MyViewHolder) helper).tv_readnum.setText(item.getVideoLookFalse());
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
        if (PayVideo>0){
            helper.shapeCornerBgView2.setVisibility(View.VISIBLE);
            if (item.getTrySee()==0){
                helper.shapeCornerBgView2.setBorderColor(Color.parseColor("#f87d28"));
                helper.shapeCornerBgView2.setTextColor(Color.parseColor("#f87d28"));
                helper.shapeCornerBgView2.setText("收费");
            }else {
                helper.shapeCornerBgView2.setBorderColor(Color.parseColor("#12cd8e"));
                helper.shapeCornerBgView2.setTextColor(Color.parseColor("#12cd8e"));
                helper.shapeCornerBgView2.setText("试看");
            }
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
        @BindView(R.id.shapeCornerBgView2)
        ShapeCornerBgView shapeCornerBgView2;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}