package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wx.goodview.GoodView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.TaskListAdapter;
import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.xinzhu.xuezhibao.presenter.TaskPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.TaskInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskActivity extends BaseActivity implements TaskInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_viplv)
    ShapeCornerBgView tvViplv;
    @BindView(R.id.tv_signin)
    TextView tvSignin;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.my_taskrv)
    RecyclerView myTaskrv;
    List<MyTaskBean> taskBean100List = new ArrayList<>();
    List<MyTaskBean> taskBean2List = new ArrayList<>();
    List<MyTaskBean> taskBean1List = new ArrayList<>();
    TaskListAdapter taskList100Adapter;
    TaskListAdapter taskList1Adapter;
    TaskListAdapter taskList2Adapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int POSITION = 0;
    TaskPresenter taskPresenter;
    int page100 = 1;
    int page1 = 1;
    int page2 = 1;
    @BindView(R.id.im_nodata)
    ImageView imNodata;
    GoodView goodView;
    boolean canshock=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        goodView = new GoodView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        tvUsername.setText(Constants.userBasicInfo.getNickName());
        tvViplv.setText(Constants.userBasicInfo.getDictionaryName());
        myTaskrv.setLayoutManager(linearLayoutManager);
        taskList1Adapter = new TaskListAdapter(new WeakReference(MyApplication.getContext()), taskBean1List);
        taskList2Adapter = new TaskListAdapter(new WeakReference(MyApplication.getContext()), taskBean2List);
        taskList100Adapter = new TaskListAdapter(new WeakReference(MyApplication.getContext()), taskBean100List);
        myTaskrv.setAdapter(taskList100Adapter);
        taskPresenter = new TaskPresenter(this);
        taskPresenter.get2task(page2);
        taskPresenter.get1task(page1);
        taskPresenter.get100task(page100);
        taskPresenter.isclochin();
        sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());

        taskList100Adapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyTaskActivity.this, MyTaskDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, taskBean100List.get(position));
                startActivityForResult(intent, 1);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        taskList1Adapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyTaskActivity.this, MyTaskDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, taskBean1List.get(position));
                startActivityForResult(intent, 1);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        taskList2Adapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyTaskActivity.this, MyTaskDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, taskBean2List.get(position));
                startActivityForResult(intent, 1);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                POSITION = tab.getPosition();
                if (POSITION == 0) {
                    if (taskBean100List.size() == 0) {
                        imNodata.setVisibility(View.VISIBLE);
                    } else {
                        imNodata.setVisibility(View.GONE);
                    }
                    myTaskrv.setAdapter(taskList100Adapter);
                } else if (POSITION == 1) {
                    if (taskBean1List.size() == 0) {
                        imNodata.setVisibility(View.VISIBLE);
                    } else {
                        imNodata.setVisibility(View.GONE);
                    }
                    myTaskrv.setAdapter(taskList1Adapter);
                } else if (POSITION == 2) {
                    if (taskBean2List.size() == 0) {
                        imNodata.setVisibility(View.VISIBLE);
                    } else {
                        imNodata.setVisibility(View.GONE);
                    }
                    myTaskrv.setAdapter(taskList2Adapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    taskPresenter.get100task(page100);
                } else if (POSITION == 1) {
                    taskPresenter.get1task(page1);
                } else if (POSITION == 2) {
                    taskPresenter.get2task(page2);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    taskBean100List.clear();
                    page100 = 1;
                    taskPresenter.get100task(page100);
                } else if (POSITION == 1) {
                    taskBean1List.clear();
                    page1 = 1;
                    taskPresenter.get1task(page1);
                } else if (POSITION == 2) {
                    taskBean2List.clear();
                    page2 = 1;
                    taskPresenter.get2task(page2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            taskBean100List.clear();
            page100 = 1;
            taskPresenter.get100task(page100);
            taskList100Adapter.notifyDataSetChanged();
            taskBean1List.clear();
            page1 = 1;
            taskPresenter.get1task(page1);
        }
    }

    @OnClick(R.id.tv_signin)
    public void onViewClicked() {
        if (canshock){
            taskPresenter.clockin();
        }else {
            BToast.success(MyTaskActivity.this).text("今日已签到，明天再来吧").show();
        }

    }

    @Override
    public void get100task(List<MyTaskBean> taskBeans) {
        page100++;
        imNodata.setVisibility(View.GONE);
        taskBean100List.addAll(taskBeans);
        taskList100Adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void get2task(List<MyTaskBean> taskBeans) {
        page2++;
        if (POSITION == 2) {
            imNodata.setVisibility(View.GONE);
        }
        taskBean2List.addAll(taskBeans);
        taskList2Adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void get1task(List<MyTaskBean> taskBeans) {
        page1++;
        if (POSITION == 1) {
            imNodata.setVisibility(View.GONE);
        }

        taskBean1List.addAll(taskBeans);
        taskList1Adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void nomoredata() {
        refreshLayout.finishLoadMoreWithNoMoreData();
        refreshLayout.finishRefresh();
    }

    @Override
    public void chocksuccessful() {
        taskPresenter.updatauserbasic();
        goodView.setTextInfo("签到成功", Color.parseColor("#ffffff"), 20);
        goodView.show(tvSignin);
        tvSignin.setText("已签到");
    }

    @Override
    public void chockfail(int code) {
        BToast.error(this).text("签到失败,错误码：" + code);
    }

    @Override
    public void ischock(String data) {
        if (data.equals("true")){
            canshock=false;
            tvSignin.setText("已签到");

        }else {
            canshock=true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskPresenter.cancelmessage();
    }
}
