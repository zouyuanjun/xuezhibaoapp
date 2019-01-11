package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseJobFoledAdapter extends BaseQuickAdapter<CourseBean, CourseJobFoledAdapter.MyViewHolder> {
    protected Context mContext;
    protected List<CourseBean> mDatas;

    public CourseJobFoledAdapter(@Nullable List<CourseBean> data) {
        super(R.layout.item_free_videofoled, data);
        this.mDatas = data;
    }

    public CourseJobFoledAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyViewHolder helper, CourseBean item) {
        helper.simpleDraweeView.setImageURI(item.getCurriculumPicture());
        helper.tvTitle.setText(item.getCurriculumTitle()+"作业集");
        helper.textView19.setText("共" + item.getCount() + "个作业");
    }


    public static class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.textView19)
        TextView textView19;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}