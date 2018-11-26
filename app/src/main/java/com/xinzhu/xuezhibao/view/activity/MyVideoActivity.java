package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
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
            int code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                if (null!=mDatas&&mDatas.size()>0){
                    videoVoiceBeanList.addAll(mDatas);
                    videoVoiceListAdapter.notifyDataSetChanged();
                    page++;
                    refreshLayout.finishLoadMore();
                }else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }

            }else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    };
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
        String data=JsonUtils.keyValueToString2("token",Constants.TOKEN,"pageNo",1);
        Network.getnetwork().postJson(data,Constants.URL+"/app/my-video",handler,1);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                String data=JsonUtils.keyValueToString2("token",Constants.TOKEN,"pageNo",page);
                Network.getnetwork().postJson(data,Constants.URL+"/app/my-video",handler,1);
            }
        });
        videoVoiceListAdapter.setOnItemClickListener(new VideoVoiceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MyVideoActivity.this,VideoDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID,videoVoiceBeanList.get(position).getVideoId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
