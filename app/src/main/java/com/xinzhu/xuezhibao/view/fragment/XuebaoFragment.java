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
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.presenter.XuebaoPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.AllCourseActivity;
import com.xinzhu.xuezhibao.view.activity.JiajiaoActivity;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.XuekeActivity;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.XuebaoInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Log;

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
    @BindView(R.id.ll_more_recommend)
    LinearLayout imMoreArticle;
    @BindView(R.id.rv_recommend_course)
    RecyclerView rvRecommendCourse;
    @BindView(R.id.im_more_all_course)
    LinearLayout imMoreAllCourse;
    @BindView(R.id.rv_all_course)
    RecyclerView rvAllCourse;
    @BindView(R.id.im_jiajiao)
    ImageView imJiajiao;
    @BindView(R.id.im_xueke)
    ImageView imXueke;
    XuebaoPresenter xuebaoPresenter;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar4;

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
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
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
    public void getHotCourse(List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHotCourse.setLayoutManager(linearLayoutManager);
        rvHotCourse.setNestedScrollingEnabled(false);
        XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
        rvHotCourse.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(), VideoDetilsActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        progressBar1.setVisibility(View.GONE);
    }

    @Override
    public void getNewCourse(List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNewCourse.setLayoutManager(linearLayoutManager);
        rvNewCourse.setNestedScrollingEnabled(false);
        XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
        rvNewCourse.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(), VideoDetilsActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        progressBar2.setVisibility(View.GONE);
    }

    @Override
    public void getRecommentCourse(List<CourseBean> list) {
        //初始化推荐课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecommendCourse.setLayoutManager(linearLayoutManager);
        rvRecommendCourse.setNestedScrollingEnabled(false);
        XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
        rvRecommendCourse.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(), VideoDetilsActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        progressBar3.setVisibility(View.GONE);
    }

    @Override
    public void getAllCourse(List<CourseBean> list) {
        //初始化最热课程列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAllCourse.setLayoutManager(linearLayoutManager);
        rvAllCourse.setNestedScrollingEnabled(false);
        XuebaoCourseAdapter courseAdapter = new XuebaoCourseAdapter(getContext(), list);
        rvAllCourse.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new XuebaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(), VideoDetilsActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        progressBar4.setVisibility(View.GONE);
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

    @OnClick({R.id.im_more_video, R.id.im_more_voice, R.id.ll_more_recommend, R.id.im_more_all_course, R.id.im_jiajiao, R.id.im_xueke})
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
            case R.id.ll_more_recommend:
                Intent intent3 = new Intent(getActivity(), AllCourseActivity.class);
                intent3.putExtra(Constants.INTENT_COURSE_CLASS, 3);
                getActivity().startActivity(intent3);
                break;
            case R.id.im_more_all_course:
                Intent intent4 = new Intent(getActivity(), AllCourseActivity.class);
                intent4.putExtra(Constants.INTENT_COURSE_CLASS, 4);
                getActivity().startActivity(intent4);
                break;
            case R.id.im_jiajiao:
                getActivity().startActivity(new Intent(getActivity(), JiajiaoActivity.class));
                break;
            case R.id.im_xueke:
                getActivity().startActivity(new Intent(getActivity(), XuekeActivity.class));
                break;
        }
    }

}
