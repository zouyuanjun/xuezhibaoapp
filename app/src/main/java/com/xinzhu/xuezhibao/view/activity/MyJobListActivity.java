package com.xinzhu.xuezhibao.view.activity;

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
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTaskAdapter;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作业列表activity
 */
public class MyJobListActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_jobname)
    TextView tvJobname;
    @BindView(R.id.tv_jobnum)
    TextView tvJobnum;
    @BindView(R.id.rv_myvideo)
    RecyclerView rvMyvideo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.im_loading)
    ImageView imLoading;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    String id;
    String title;
    String num;
    int page = 1;
    RvJiaojiaoTaskAdapter rvJiaojiaoTaskAdapter;
    List<MyjobBean> list = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code = -999;
            String tip;
            try {
                code = JsonUtils.getIntValue(result, "Code");
                tip = JsonUtils.getStringValue(result, "Tips");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                return;
            }

            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                List<MyjobBean> mDatas = JSON.parseArray(data, MyjobBean.class);
                if (null != mDatas && mDatas.size() > 0) {
                    adddata(mDatas);

                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjoblist);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra(Constants.INTENT_ID);
        title = getIntent().getStringExtra(Constants.INTENT_ID2);
        num = getIntent().getStringExtra(Constants.INTENT_ID3);
        tvJobname.setText("课程：" + title);
        tvJobnum.setText(num + "个作业");
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyvideo.setLayoutManager(linearLayoutManager3);
        rvJiaojiaoTaskAdapter = new RvJiaojiaoTaskAdapter(this, list);
        rvMyvideo.setAdapter(rvJiaojiaoTaskAdapter);
        rvJiaojiaoTaskAdapter.setOnItemClickListener(new RvJiaojiaoTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyApplication.getContext(), CourseTaskActivity.class);
                intent.putExtra(Constants.INTENT_ID, list.get(position).getJobReplyId());

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getjob(page);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                list.clear();
                rvJiaojiaoTaskAdapter.notifyDataSetChanged();
                getjob(1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationDrawable animationDrawable = (AnimationDrawable) imLoading.getDrawable();
        animationDrawable.start();
        list.clear();
        page = 1;
        getjob(1);
    }

    //获取作业列表
    public void getjob(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "curriculumId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-Job", handler, 4);
    }

    public void adddata(List<MyjobBean> mDatas) {
        if (null != refreshLayout) {
            imLoading.setVisibility(View.GONE);
            list.addAll(mDatas);
            if (list.size() == 0) {
                imDataisnull.setVisibility(View.VISIBLE);
            }
            rvJiaojiaoTaskAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
        }
    }
}
