package com.xinzhu.xuezhibao.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.HomeArticleAdapter;
import com.xinzhu.xuezhibao.adapter.HomeVideoAdapter;
import com.xinzhu.xuezhibao.adapter.HomeVoiceAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.VideoBean;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.presenter.HomepagePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.ArticleDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.HomeListActivity;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.activity.SettingActivity;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.VoiceDetilsActivity;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.HomepageInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zou.fastlibrary.ui.ClearWriteEditText;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragemt extends LazyLoadFragment implements HomepageInterface {
    int messagecount = 0;
    Unbinder unbinder;
    HomepagePresenter homepagePresenter;
    QBadgeView qBadgeView;
    @BindView(R.id.im_scan)
    ImageView tvMessage;
    @BindView(R.id.ed_search)
    ClearWriteEditText edSearch;
    @BindView(R.id.im_setting)
    ImageView imSetting;
    @BindView(R.id.im_message)
    ImageView imMessage;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.im_test)
    ImageView imTest;
    @BindView(R.id.im_more_video)
    ImageView imMoreVideo;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.im_more_voice)
    ImageView imMoreVoice;
    @BindView(R.id.rv_voice)
    RecyclerView rvVoice;
    @BindView(R.id.im_more_article)
    ImageView imMoreArticle;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    HomeVideoAdapter homeVideoAdapter;
    HomeArticleAdapter homeArticleAdapter;
    HomeVoiceAdapter homeVoiceAdapter;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        Log.d("懒加载开始");
        EditTextUtil.hideKeyboard(MyApplication.getContext(),edSearch);
        homepagePresenter=new HomepagePresenter(this,MyApplication.getContext());
        homepagePresenter.initdata();
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
        qBadgeView = new QBadgeView(MyApplication.getContext());
        qBadgeView.bindTarget(imMessage).setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        JMessageClient.registerEventReceiver(this);
        Log.d("创建完毕");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void onEvent(MessageEvent event) {
        //  event.getMessage().toJson();
        messagecount++;
        qBadgeView.setBadgeNumber(messagecount);
        Log.d("收到l一条消息" + messagecount);
    }

    @OnClick({R.id.im_scan, R.id.ed_search, R.id.im_setting, R.id.im_message, R.id.banner, R.id.im_test, R.id.ll_more_video, R.id.rv_video, R.id.ll_more_voice, R.id.rv_voice, R.id.ll_more_article, R.id.rv_article})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.im_scan:
                startScanQR();
                break;
            case R.id.ed_search:
                break;
            case R.id.im_setting:
                startActivity(new Intent(getContext(),SettingActivity.class));
                break;
            case R.id.im_message:
                qBadgeView.setBadgeNumber(0);
                startActivity(new Intent(getContext(),ConversationListActivity.class));
                break;
            case R.id.banner:
                break;
            case R.id.im_test:
                break;
            case R.id.ll_more_video:
                Intent intent=new Intent(getContext(),HomeListActivity.class);
                intent.putExtra("TYPE",1);
                startActivity(intent);
                break;
            case R.id.ll_more_voice:
                Intent intent2=new Intent(getContext(),HomeListActivity.class);
                intent2.putExtra("TYPE",2);
                startActivity(intent2);
                break;
            case R.id.ll_more_article:
                Intent intent3=new Intent(getContext(),HomeListActivity.class);
                intent3.putExtra("TYPE",3);
                startActivity(intent3);
                break;
        }
    }
    private List<String> initimg(){
        List<String> mDatas=new ArrayList<>();
        String url="http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        mDatas.add(url);
        mDatas.add("http://pic14.nipic.com/20110605/1369025_165540642000_2.jpg");
        mDatas.add("http://img.zcool.cn/community/01f39a59a7affba801211d25185cd3.jpg@1280w_1l_2o_100sh.jpg");
        mDatas.add("http://pic33.photophoto.cn/20141022/0019032438899352_b.jpg");
        mDatas.add(url);
        mDatas.add("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg");
        mDatas.add(url);
        mDatas.add("http://pic24.nipic.com/20121010/3798632_184253198370_2.jpg");
        return  mDatas;
    }

    @Override
    public void getVideodata(final List<VideoBean> mDatas) {
        //初始化视频列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvVideo.setLayoutManager(linearLayoutManager);
        rvVideo.setNestedScrollingEnabled(false);
        homeVideoAdapter=new HomeVideoAdapter(MyApplication.getContext(),mDatas);
        rvVideo.setAdapter(homeVideoAdapter);
        homeVideoAdapter.setOnItemClickListener(new HomeVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id=mDatas.get(position).getVideoId();
                Intent intent=new Intent(getContext(),VideoDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID,id);
                getActivity().startActivity(intent);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void getVoicedata(final List<VideoBean> mDatas) {

        //初始化音频列表
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvVoice.setNestedScrollingEnabled(false);
        rvVoice.setLayoutManager(linearLayoutManager2);
        homeVoiceAdapter=new HomeVoiceAdapter(MyApplication.getContext(),mDatas);
        rvVoice.setAdapter(homeVoiceAdapter);
        homeVoiceAdapter.setOnItemClickListener(new HomeVoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id=mDatas.get(position).getVideoId();
                Intent intent=new Intent(getContext(),VoiceDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID,id);
                getActivity().startActivity(intent);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void getArticle(final List<ArticleBean> mDatas) {
        //初始化文章列表
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvArticle.setLayoutManager(linearLayoutManager3);
        rvArticle.setNestedScrollingEnabled(false);
        homeArticleAdapter=new HomeArticleAdapter(MyApplication.getContext(),mDatas);
        rvArticle.setAdapter(homeArticleAdapter);
        homeArticleAdapter.setOnItemClickListener(new HomeArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id=mDatas.get(position).getArticleId();
                Intent intent=new Intent(getActivity(),ArticleDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID,id);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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
        startActivityForResult(new Intent(getActivity(),QRActivity.class),1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null!=data){
            String qrcode=data.getStringExtra("RESULT_QRCODE_STRING");
            Log.d(qrcode);
        }


    }

    public void startScanQR(){
        if (EasyPermissions.hasPermissions(getContext(),Manifest.permission.CAMERA )){
            startActivityForResult(new Intent(getContext(),QRActivity.class),1);
        }else {
            EasyPermissions.requestPermissions(getActivity(), "使用扫一扫功能需要允许相机权限哦！",1,Manifest.permission.CAMERA);
        }
    }
}
