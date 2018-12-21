package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFolderAdapter extends BaseQuickAdapter<VideoFolder, VideoFolderAdapter.ViewHolder> {
    protected Context mContext;
    protected List<VideoFolder> mDatas;

    public VideoFolderAdapter(int layoutResId, @Nullable List<VideoFolder> data) {
        super(R.layout.item_video_folder, data);
        this.mDatas = data;
    }

    public VideoFolderAdapter(@Nullable List<VideoFolder> data) {
        super(data);
    }

    public VideoFolderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, VideoFolder item) {
        helper.simpleDraweeView.setImageURI(item.getVideoTypeImage());
        helper.tvTitle.setText(item.getVideoTypeName());
        helper.tvPaynum.setText(item.getVideoTypeNum() + "人已购买");
        helper.textView17.setText("￥"+item.getVideoTypeMoney());
        helper.textView19.setText("(共"+item.getVideoTypeNum()+"节)");
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.tv_readnum)
        TextView tvReadnum;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_paynum)
        TextView tvPaynum;
        @BindView(R.id.shapeCornerBgView)
        ShapeCornerBgView shapeCornerBgView;
        @BindView(R.id.textView19)
        TextView textView19;
        @BindView(R.id.textView17)
        TextView textView17;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}