package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.StatusBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.vp_photoview)
    ViewPager vpPhotoview;
    List<FeedbackPictureBean> myjobBean;
    List<ImageView> list = new ArrayList<>();
    int poisition;
    int index;
    int allindex;
    @BindView(R.id.tv_index)
    TextView tvIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setTranslucent(this);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);
        myjobBean = (List<FeedbackPictureBean>) getIntent().getSerializableExtra(Constants.INTENT_ID);
        poisition = getIntent().getIntExtra(Constants.INTENT_ID2, 0);
        for (FeedbackPictureBean feedbackPictureBean : myjobBean) {
            ImageView imageView = new ImageView(this);
            Glide.with(MyApplication.getContext()).load(feedbackPictureBean.getAccessoryUrl()).into(imageView);
            list.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        allindex=myjobBean.size();
        vpPhotoview.setAdapter(new MyPagerAdapter(list));
        vpPhotoview.setCurrentItem(poisition);
        tvIndex.setText(poisition+1+"/"+allindex);
        vpPhotoview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndex.setText(position+1+"/"+allindex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class MyPagerAdapter extends PagerAdapter {

        List<ImageView> viewList = null;

        public MyPagerAdapter(List<ImageView> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));//添加页卡
            return viewList.get(position);
        }
    }

}
