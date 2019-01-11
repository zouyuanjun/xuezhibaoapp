package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.bean.VideoFolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrideViewAdapter extends BaseQuickAdapter<FeedbackPictureBean, GrideViewAdapter.ViewHolder> {
    protected Context mContext;
    protected List<FeedbackPictureBean> mDatas;
    public GrideViewAdapter(@Nullable List<FeedbackPictureBean> data) {
        super(R.layout.item_img, data);
        this.mDatas = data;
    }
    public GrideViewAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, FeedbackPictureBean item) {
     //   helper.simpleDraweeView.setImageURI(item.getAccessoryUrl());
        Glide.with(MyApplication.getContext()).load(item.getAccessoryUrl()).into( helper.simpleDraweeView);

    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.simpleDraweeView)
        ImageView simpleDraweeView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
