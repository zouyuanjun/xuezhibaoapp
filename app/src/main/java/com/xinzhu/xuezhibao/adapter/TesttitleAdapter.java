package com.xinzhu.xuezhibao.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.TestBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TesttitleAdapter extends BaseQuickAdapter<TestBean, TesttitleAdapter.ViewHolder> {

    public TesttitleAdapter(@Nullable List<TestBean> data) {
        super(R.layout.item_testtitle, data);
    }

    @Override
    protected void convert(ViewHolder helper, TestBean item) {
        helper.tvJoinnum33.setText(item.getDictionaryValue() + "人已测评");
        helper.imageView33.setImageURI(item.getDictionaryImage());
        helper.textView533.setText(item.getDictionaryName());
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imageView33)
        SimpleDraweeView imageView33;
        @BindView(R.id.textView533)
        TextView textView533;
        @BindView(R.id.tv_joinnum33)
        TextView tvJoinnum33;
        @BindView(R.id.csl_3)
        ConstraintLayout csl3;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
