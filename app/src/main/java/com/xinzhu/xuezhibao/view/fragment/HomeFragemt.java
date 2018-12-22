package com.xinzhu.xuezhibao.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bravin.btoast.BToast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.HomeArticleAdapter;
import com.xinzhu.xuezhibao.adapter.HomeVideoAdapter;
import com.xinzhu.xuezhibao.adapter.HomeVoiceAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.immodule.JGApplication;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.presenter.HomepagePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.ArticleDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.HomeListActivity;
import com.xinzhu.xuezhibao.view.activity.LoginActivity;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.activity.SettingActivity;
import com.xinzhu.xuezhibao.view.activity.TestIntroduceActivity;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.VoiceDetilsActivity;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.HomepageInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.WebActivity;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.api.BasicCallback;
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
    EditText edSearch;
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
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    List<BannerImgBean> mybannerImgBean = new ArrayList<>();
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int whith;
    int height;
    List<String> mDatas = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        Log.d("懒加载开始");
        EditTextUtil.hideKeyboard(MyApplication.getContext(), edSearch);

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
                if (position < mybannerImgBean.size()) {
                    if (mybannerImgBean.get(position).getNewPlace() == 1) {
                        Uri uri = Uri.parse(mybannerImgBean.get(position).getLinkAddress());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("URL", mybannerImgBean.get(position).getLinkAddress());
                        // intent.putExtra("URL", "http://soft.imtt.qq.com/browser/tes/feedback.html");
                        startActivity(intent);
                    }

                }

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homepagePresenter.initdata();
                refreshLayout.finishRefresh(2000);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != JMessageClient.getMyInfo()) {
            qBadgeView = new QBadgeView(MyApplication.getContext());
            qBadgeView.bindTarget(llMessage).setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
            messagecount = JMessageClient.getAllUnReadMsgCount();
            qBadgeView.setBadgeNumber(messagecount);
        }
        homepagePresenter.initdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        JMessageClient.registerEventReceiver(new WeakReference<>(this).get());
        homepagePresenter = new HomepagePresenter(this, MyApplication.getContext());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JMessageClient.unRegisterEventReceiver(new WeakReference<>(this).get());
        homepagePresenter.cancelmessage();
        unbinder.unbind();
    }

    public void onEvent(MessageEvent event) {
        //  event.getMessage().toJson();
        messagecount = JMessageClient.getAllUnReadMsgCount();
        qBadgeView.setBadgeNumber(messagecount);
        Log.d("收到l一条消息" + messagecount);
    }

    public void onEvent(LoginStateChangeEvent event) {
        LoginStateChangeEvent.Reason message = event.getReason();
        if (message.name().equals("user_logout")) {
            BToast.error(getContext()).text("您从其他客户端登陆，本客户端已下线").show();
        }
    }

    //通知栏点击事件
    public void onEvent(NotificationClickEvent event) {
        Intent notificationIntent = new Intent(getContext(), ChatActivity.class);
        notificationIntent.putExtra(JGApplication.TARGET_ID, event.getMessage().getTargetID());
        notificationIntent.putExtra(JGApplication.CONV_TITLE, event.getMessage().getTargetName());
        notificationIntent.putExtra(JGApplication.TARGET_APP_KEY, event.getMessage().getTargetAppKey());
        startActivity(notificationIntent);//自定义跳转到指定页面
    }

    @OnClick({R.id.im_scan, R.id.ed_search, R.id.im_setting, R.id.im_message, R.id.banner, R.id.im_test, R.id.ll_more_video, R.id.rv_video, R.id.ll_more_voice, R.id.rv_voice, R.id.ll_more_article, R.id.rv_article})
    public void onViewClicked(View view) {
        if (Constants.TOKEN.isEmpty()) {
            shoudia();
            return;
        }
        switch (view.getId()) {
            case R.id.im_scan:
                startScanQR();
                break;
            case R.id.ed_search:
                break;
            case R.id.im_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.im_message:
                if (Constants.TOKEN.isEmpty()) {
                    shoudia();
                } else if (null == JMessageClient.getMyInfo()) {
                    JMessageClient.login(Constants.userBasicInfo.getAccount(), "xzb123456", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            android.util.Log.d("JIM登陆响应", responseCode + responseMessage);
                            if (responseCode == 0) {
                                BToast.success(getContext()).text("聊天服务器登陆成功了");
                                //注册时更新头像
                            } else {
                                BToast.error(getContext()).text("抱歉，聊天服务器出现故障，您将只能查看历史消息");
                            }
                            startActivity(new Intent(getContext(), ConversationListActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(getContext(), ConversationListActivity.class));
                }
                break;
            case R.id.banner:
                break;
            case R.id.im_test:
                if (Constants.TOKEN.isEmpty()) {
                    shoudia();
                } else{
                    Intent intent11 = new Intent(getContext(), TestIntroduceActivity.class);
                    startActivity(intent11);
                }
                break;
            case R.id.ll_more_video:
                Intent intent = new Intent(getContext(), HomeListActivity.class);
                intent.putExtra("TYPE", 1);
                startActivity(intent);
                break;
            case R.id.ll_more_voice:
                Intent intent2 = new Intent(getContext(), HomeListActivity.class);
                intent2.putExtra("TYPE", 2);
                startActivity(intent2);
                break;
            case R.id.ll_more_article:
                Intent intent3 = new Intent(getContext(), HomeListActivity.class);
                intent3.putExtra("TYPE", 3);
                startActivity(intent3);
                break;
        }
    }

    public void shoudia() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage("登陆后才可以继续操作，现在就去登陆");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra(Constants.FROMAPP, "fss");
                startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    //初始化视频列表
    @Override
    public void getVideodata(final List<VideoVoiceBean> mDatas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (null != rvVideo) {
            refreshLayout.finishRefresh();
            rvVideo.setLayoutManager(linearLayoutManager);
            rvVideo.setNestedScrollingEnabled(false);
            homeVideoAdapter = new HomeVideoAdapter(MyApplication.getContext(), mDatas);
            rvVideo.setAdapter(homeVideoAdapter);
            homeVideoAdapter.setOnItemClickListener(new HomeVideoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (StringUtil.isEmpty(Constants.TOKEN)){
                        DialogUtils.loginDia(getActivity());
                        return;
                    }
                    String id = mDatas.get(position).getVideoId();
                    Intent intent = new Intent(getContext(), VideoDetilsActivity.class);
                    intent.putExtra(Constants.INTENT_ID, id);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

    }

    @Override
    public void getVoicedata(final List<VideoVoiceBean> mDatas) {
        if (null != rvVoice) {
//        初始化音频列表
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MyApplication.getContext());
            linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (null != rvVoice) {
                rvVoice.setNestedScrollingEnabled(false);
                rvVoice.setLayoutManager(linearLayoutManager2);
                homeVoiceAdapter = new HomeVoiceAdapter(MyApplication.getContext(), mDatas);
                rvVoice.setAdapter(homeVoiceAdapter);
                homeVoiceAdapter.setOnItemClickListener(new HomeVoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (StringUtil.isEmpty(Constants.TOKEN)){
                            DialogUtils.loginDia(getActivity());
                            return;
                        }
                        String id = mDatas.get(position).getVideoId();
                        Intent intent = new Intent(getContext(), VoiceDetilsActivity.class);
                        intent.putExtra(Constants.INTENT_ID, id);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }

        }
    }

    //初始化文章列表
    @Override
    public void getArticle(final List<ArticleBean> mDatas) {

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        if (null != rvArticle) {
            rvArticle.setLayoutManager(linearLayoutManager3);
            rvArticle.setNestedScrollingEnabled(false);
            homeArticleAdapter = new HomeArticleAdapter(MyApplication.getContext(), mDatas);
            rvArticle.setAdapter(homeArticleAdapter);
            homeArticleAdapter.setOnItemClickListener(new HomeArticleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (StringUtil.isEmpty(Constants.TOKEN)){
                        DialogUtils.loginDia(getActivity());
                        return;
                    }
                    String id = mDatas.get(position).getArticleId();
                    Intent intent = new Intent(getActivity(), ArticleDetilsActivity.class);
                    intent.putExtra(Constants.INTENT_ID, id);
                    getActivity().startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

    }

    @Override
    public void getbanner(List<BannerImgBean> bannerImgBeans) {


        mybannerImgBean.clear();
        for (BannerImgBean bannerImgBean : bannerImgBeans) {
            mDatas.add(bannerImgBean.getAdUrl());
            mybannerImgBean.add(bannerImgBean);
        }
        if (null != banner) {
            Glide.with(MyApplication.getContext())
                    .asBitmap()
                    .load(mDatas.get(0))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //  imLogo.setImageBitmap(resource);
                           whith=resource.getWidth();
                           height=resource.getHeight();
                            ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                            int width=(int)((getContext().getResources().getDisplayMetrics().widthPixels));
                            layoutParams.width = width;
                            layoutParams.height = (width/whith)*height;
                            //      Log.d(layoutParams.width+"高度是"+layoutParams.height+"原始"+height+"级"+context.getResources().getDisplayMetrics().density);
                            banner.setLayoutParams(layoutParams);

                            banner.setImages(mDatas);
                            banner.start();
                        }
                    });

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
            BToast.info(getContext()).text("扫描结果：" + qrcode).show();
        }


    }

    public void startScanQR() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA)) {
            startActivityForResult(new Intent(getContext(), QRActivity.class), 1);
        } else {
            EasyPermissions.requestPermissions(getActivity(), "使用扫一扫功能需要允许相机权限哦！", 1, Manifest.permission.CAMERA);
        }
    }
}
