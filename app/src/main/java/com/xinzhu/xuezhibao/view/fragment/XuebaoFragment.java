package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.XuebaoCourseAdapter;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.presenter.XuebaoPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.AllCourseActivity;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.MyFamilyCourseActivity;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.activity.MySubjectActivity;
import com.xinzhu.xuezhibao.view.helputils.CreatDiag;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.XuebaoInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;

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
    @BindView(R.id.im_xueke)
    ImageView imXueke;
    XuebaoPresenter xuebaoPresenter;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    List<BannerImgBean> bannerImgBeans=new ArrayList<>();
    List<String> bannerlist=new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_xuebao;
    }

    @Override
    protected void lazyLoad() {
        xuebaoPresenter = new XuebaoPresenter(this);
        xuebaoPresenter.initdata();
        Log.d("懒加载开始");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(initimg());
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Log.d("创建完毕");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private List<String> initimg() {
        List<String> mDatas = new ArrayList<>();
        String url = "http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        mDatas.add(url);
        mDatas.add("http://pic14.nipic.com/20110605/1369025_165540642000_2.jpg");
        mDatas.add("http://img.zcool.cn/community/01f39a59a7affba801211d25185cd3.jpg@1280w_1l_2o_100sh.jpg");
        mDatas.add("http://pic33.photophoto.cn/20141022/0019032438899352_b.jpg");
        mDatas.add(url);
        mDatas.add("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg");
        mDatas.add(url);
        mDatas.add("http://pic24.nipic.com/20121010/3798632_184253198370_2.jpg");
        return mDatas;
    }

    @Override
    public void getHotCourse(final List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (null!=rvHotCourse){
            rvHotCourse.setLayoutManager(linearLayoutManager);
            rvHotCourse.setNestedScrollingEnabled(false);
            XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
            rvHotCourse.setAdapter(courseAdapter);
            courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent=new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra(Constants.INTENT_ID,list.get(position).getCurriculumId());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            progressBar1.setVisibility(View.GONE);
        }

    }

    @Override
    public void getNewCourse(final List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (null!=rvNewCourse){
            rvNewCourse.setLayoutManager(linearLayoutManager);
            rvNewCourse.setNestedScrollingEnabled(false);
            XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
            rvNewCourse.setAdapter(courseAdapter);
            courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent=new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra(Constants.INTENT_ID,list.get(position).getCurriculumId());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            progressBar2.setVisibility(View.GONE);
        }

    }
    @Override
    public void getBanner(List<BannerImgBean> list) {
        if (null==list&&list.size()==0){
            return;
        }
        if (null!=banner){
            bannerlist.clear();
            for (BannerImgBean bannerImgBean:list){
                bannerlist.add(bannerImgBean.getAdUrl());
                bannerImgBeans.add(bannerImgBean);
            }
            banner.setImages(bannerlist);
            banner.start();
        }

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

    @OnClick({R.id.im_more_video, R.id.im_more_voice, R.id.im_jiajiao, R.id.im_xueke})
    public void onViewClicked(View view) {
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
            case R.id.im_jiajiao:
                if (StringUtil.isEmpty(Constants.TOKEN)){
                    CreatDiag.shoudia(getActivity());

                }else {
                    getActivity().startActivity(new Intent(getActivity(), MyFamilyCourseActivity.class));
                }

                break;
            case R.id.im_xueke:
                if (StringUtil.isEmpty(Constants.TOKEN)){
                    CreatDiag.shoudia(getActivity());
                }else {
                    getActivity().startActivity(new Intent(getActivity(), MySubjectActivity.class));
                }

                break;
        }
    }

}
