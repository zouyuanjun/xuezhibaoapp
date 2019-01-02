package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFreeFolderAdapter extends BaseQuickAdapter<VideoFolder, VideoFreeFolderAdapter.ViewHolder> {
    protected Context mContext;
    protected List<VideoFolder> mDatas;
    public VideoFreeFolderAdapter( @Nullable List<VideoFolder> data) {
        super(R.layout.item_free_videofoled, data);
        this.mDatas = data;
    }


    public VideoFreeFolderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, VideoFolder item) {
        helper.simpleDraweeView.setImageURI(item.getVideoTypeImage());
        helper.tvTitle.setText(item.getVideoTypeName());
        helper.textView19.setText("(共"+item.getCount()+"节)");
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.tv_readnum)
        TextView tvReadnum;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.textView19)
        TextView textView19;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
