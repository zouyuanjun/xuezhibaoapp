package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.VideoFragmentInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 精品视频
 */
public class VideoListActivity extends BaseActivity implements VideoFragmentInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    VideoVoiceListAdapter payadapter;
    Unbinder unbinder;
    List<VideoVoiceBean> payBeanList = new ArrayList<>();
    int paypage = 1;
    @BindView(R.id.rv_item)
    RecyclerView rvVideocourselist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter videoVoiceListPresenter;
    @BindView(R.id.img_nodata)
    ImageView imgNodata;
    String videoid;
    String title;
    int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homevideocourse);
        ButterKnife.bind(this);
        videoid=getIntent().getStringExtra(Constants.INTENT_ID);
        title=getIntent().getStringExtra(Constants.INTENT_ID2);
        type=getIntent().getIntExtra(Constants.INTENT_ID3,0);
        appbar.setMidText(title);
        videoVoiceListPresenter = new VideoVoiceListPresenter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(VideoListActivity.this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        if (type==1){
            payadapter = new VideoVoiceListAdapter(new WeakReference<>(VideoListActivity.this).get(), payBeanList, 2,0);

        }else {
            payadapter = new VideoVoiceListAdapter(new WeakReference<>(VideoListActivity.this).get(), payBeanList, 2,2);

        }
         rvVideocourselist.setAdapter(payadapter);
        payadapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                payBeanList.clear();
                paypage = 1;
                videoVoiceListPresenter.getpayVideo(videoid);
            }
        });
        refreshLayout.setEnableLoadMore(false);
        videoVoiceListPresenter.getpayVideo(videoid);
        payadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String id = payBeanList.get(position).getVideoId();
                Intent intent = new Intent(VideoListActivity.this, VideoDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID, id);
                startActivity(intent);
            }
        });
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void getpayVideo(List<VideoVoiceBean> List) {
        if (null != refreshLayout) {
            payBeanList.addAll(List);
            payadapter.notifyDataSetChanged();
            paypage++;
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh(true);
        }

    }

    @Override
    public void noData() {
        if (null != refreshLayout) {
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
        if (payBeanList.size() == 0) {
            imgNodata.setVisibility(View.VISIBLE);
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
}
