package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFeedbackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;
    @BindView(R.id.linearLayout10)
    LinearLayout linearLayout10;
    @BindView(R.id.im_back1)
    ImageView imBack1;
    @BindView(R.id.im_back2)
    ImageView imBack2;
    @BindView(R.id.im_back3)
    ImageView imBack3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyfeedback);
        ButterKnife.bind(this);
        Glide.with(this).load("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1539945159&di=09e92627d4a71dc000deea4ec7154209&src=http://img4q.duitang.com/uploads/blog/201404/17/20140417225143_aWWfi.jpeg").
                into(im1);
        Glide.with(this).load("http://img3.imgtn.bdimg.com/it/u=2200166214,500725521&fm=26&gp=0.jpg").
                into(im2);
        Glide.with(this).load("http://a3.topitme.com/5/db/ff/11527264392c7ffdb5o.jpg").
                into(im3);
        Glide.with(this).load("http://img3.imgtn.bdimg.com/it/u=2200166214,500725521&fm=26&gp=0.jpg").
                into(imBack1);
        Glide.with(this).load("http://pic2.ooopic.com/10/50/25/52b1OOOPICa3.jpg").
                into(imBack2);
        Glide.with(this).load("http://img3.imgtn.bdimg.com/it/u=2200166214,500725521&fm=26&gp=0.jpg").
                into(imBack3);



    }
}
