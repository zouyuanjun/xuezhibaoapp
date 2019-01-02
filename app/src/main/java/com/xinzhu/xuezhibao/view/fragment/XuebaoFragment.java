package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.XuebaoCourseAdapter;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.presenter.XuebaoPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.AllCourseActivity;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.MyFamilyCourseActivity;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.XuebaoInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zou.fastlibrary.bean.NetWorkMessage;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.WebActivity;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;

/**
 * 学宝
 */
public class XuebaoFragment extends LazyLoadFragment implements XuebaoInterface {
    int messagecount = 0;
    Unbinder unbinder;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.im_more_video)
    LinearLayout imMoreVideo;
    @BindView(R.id.rv_hot_course)
    RecyclerView rvHotCourse;
    @BindView(R.id.im_more_voice)
    LinearLayout imMoreVoice;
    @BindView(R.id.rv_new_course)
    RecyclerView rvNewCourse;
    @BindView(R.id.im_jiajiao)
    ImageView imJiajiao;
    XuebaoPresenter xuebaoPresenter;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    List<BannerImgBean> bannerImgBeans = new ArrayList<>();
    List<String> bannerlist = new ArrayList<>();
    @BindView(R.id.nodataimg1)
    ImageView nodataimg1;
    @BindView(R.id.nodataimg2)
    ImageView nodataimg2;

    @Override
    protected int setContentView() {
        return R.layout.fragment_xuebao;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Log.d("创建完毕");
        xuebaoPresenter = new XuebaoPresenter(this);
        xuebaoPresenter.initdata();
        Log.d("懒加载开始");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //   banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (StringUtil.isEmpty(Constants.TOKEN)){
                    DialogUtils.loginDia(getActivity());
                    return;
                }
                if (position < bannerImgBeans.size()) {
                    if (bannerImgBeans.get(position).getNewPlace() == 1) {
                        Uri uri = Uri.parse(bannerImgBeans.get(position).getLinkAddress());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("URL", bannerImgBeans.get(position).getLinkAddress());
                        startActivity(intent);
                    }

                }

            }
        });

        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //初始化最热课程列表
    @Override
    public void getHotCourse(final List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (null != rvHotCourse) {
            rvHotCourse.setLayoutManager(linearLayoutManager);
            rvHotCourse.setNestedScrollingEnabled(false);
            XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
            rvHotCourse.setAdapter(courseAdapter);
            courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (StringUtil.isEmpty(Constants.TOKEN)){
                        DialogUtils.loginDia(getActivity());
                        return;
                    }
                    Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra(Constants.INTENT_ID, list.get(position).getCurriculumId());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            progressBar1.setVisibility(View.GONE);
            nodataimg1.setVisibility(View.GONE);
        }
    }
    @Override
    public void getNewCourse(final List<CourseBean> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (null != rvNewCourse) {
            rvNewCourse.setLayoutManager(linearLayoutManager);
            rvNewCourse.setNestedScrollingEnabled(false);
            XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
            rvNewCourse.setAdapter(courseAdapter);
            courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (StringUtil.isEmpty(Constants.TOKEN)){
                        DialogUtils.loginDia(getActivity());
                        return;
                    }
                    Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra(Constants.INTENT_ID, list.get(position).getCurriculumId());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            progressBar2.setVisibility(View.GONE);
            nodataimg2.setVisibility(View.GONE);
        }

    }

    @Override
    public void getBanner(List<BannerImgBean> list) {
        if (null == list && list.size() == 0) {
            return;
        }
        if (null != banner) {
            bannerlist.clear();
            bannerImgBeans.clear();
            for (BannerImgBean bannerImgBean : list) {
                bannerlist.add(bannerImgBean.getAdUrl());
                bannerImgBeans.add(bannerImgBean);
            }
            Glide.with(MyApplication.getContext())
                    .asBitmap()
                    .load(list.get(0).getAdUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //  imLogo.setImageBitmap(resource);
                            int whith=resource.getWidth();
                          int   height=resource.getHeight();
                            ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                            int width=(int)((getContext().getResources().getDisplayMetrics().widthPixels));
                            layoutParams.width = width;
                            float h=((float) width/whith)*height;
                            int hh=(int) h;
                            layoutParams.height =hh;
                            //      Log
                            // .d(layoutParams.width+"高度是"+layoutParams.height+"原始"+height+"级"+context.getResources().getDisplayMetrics().density);
                            banner.setLayoutParams(layoutParams);

                            banner.setImages(bannerlist);
                            banner.start();
                        }
                    });
        }

    }

    @Override
    public void noHotCourse() {
        progressBar1.setVisibility(View.GONE);
        nodataimg1.setVisibility(View.VISIBLE);

    }

    @Override
    public void noNewCourse() {
        progressBar2.setVisibility(View.GONE);
        nodataimg2.setVisibility(View.VISIBLE);
    }


    @Override
    public void networktimeout() {
        super.networktimeout();
    }

    @Override
    public void networkerr() {
        super.networkerr();
    }

    @Override
    public void servererr() {
        super.servererr();
    }

    @AfterPermissionGranted(1)
    public void onPermissionsGranted() {
        startActivityForResult(new Intent(getActivity(), QRActivity.class), 1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            String qrcode = data.getStringExtra("RESULT_QRCODE_STRING");
            Log.d(qrcode);
        }


    }

    @OnClick({R.id.im_more_video, R.id.im_more_voice, R.id.im_jiajiao})
    public void onViewClicked(View view) {
        if (StringUtil.isEmpty(Constants.TOKEN)){
            DialogUtils.loginDia(getActivity());
            return;
        }
        switch (view.getId()) {
            case R.id.im_more_video:
                Intent intent1 = new Intent(getActivity(), AllCourseActivity.class);
                intent1.putExtra(Constants.INTENT_COURSE_CLASS, 1);
                getActivity().startActivity(intent1);
                break;
            case R.id.im_more_voice:
                Intent intent2 = new Intent(getActivity(), AllCourseActivity.class);
                intent2.putExtra(Constants.INTENT_COURSE_CLASS, 2);
                getActivity().startActivity(intent2);
                break;
            case R.id.im_jiajiao:   //成长之路
                Intent intent3 = new Intent(getActivity(), MyFamilyCourseActivity.class);
                getActivity().startActivity(intent3);
                break;
        }
    }

}
