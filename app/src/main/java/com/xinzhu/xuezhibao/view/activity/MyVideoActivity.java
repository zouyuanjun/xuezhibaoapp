package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我购买的视频
 */
public class MyVideoActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.rv_myvideo)
    RecyclerView rvMyvideo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<VideoVoiceBean> videoVoiceBeanList = new ArrayList<>();
    VideoVoiceListAdapter videoVoiceListAdapter;
    int page = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            animationDrawable.stop();
            imLoading.setVisibility(View.GONE);
            int code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                if (null != mDatas && mDatas.size() > 0) {
                    if (page==1){
                        videoVoiceBeanList.clear();
                    }
                    videoVoiceBeanList.addAll(mDatas);
                    videoVoiceListAdapter.notifyDataSetChanged();

                    page++;
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                } else {
                    if (videoVoiceBeanList.size()==0){
                        imDataisnull.setVisibility(View.VISIBLE);
                    }
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    refreshLayout.finishRefresh();
                }

            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    };
    @BindView(R.id.im_loading)
    ImageView imLoading;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    AnimationDrawable animationDrawable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvideo);
        ButterKnife.bind(this);
        videoVoiceListAdapter = new VideoVoiceListAdapter(new WeakReference<Context>(this), videoVoiceBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyvideo.setLayoutManager(linearLayoutManager);
        rvMyvideo.setAdapter(videoVoiceListAdapter);
        String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "pageNo", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-video", handler, 1);
         animationDrawable= (AnimationDrawable) imLoading.getDrawable();
        animationDrawable.start();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "pageNo", page);
                Network.getnetwork().postJson(data, Constants.URL + "/app/my-video", handler, 1);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "pageNo", 1);
                Network.getnetwork().postJson(data, Constants.URL + "/app/my-video", handler, 1);
            }

        });
        videoVoiceListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyVideoActivity.this, VideoDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID, videoVoiceBeanList.get(position).getVideoId());
                startActivity(intent);
            }
        });
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
