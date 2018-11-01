package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.TaskListAdapter;
import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskActivity extends BaseActivity {
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myTaskrv.setLayoutManager(linearLayoutManager);
        initdata();
    }

    @OnClick(R.id.tv_signin)
    public void onViewClicked() {
    }
    public void initdata(){
        List<MyTaskBean> myTaskBeans=new ArrayList<>();
        MyTaskBean myTaskBean=new MyTaskBean("122","SDAFASDF","6积分");
        myTaskBeans.add(myTaskBean);
        myTaskBeans.add(myTaskBean); myTaskBeans.add(myTaskBean); myTaskBeans.add(myTaskBean); myTaskBeans.add(myTaskBean); myTaskBeans.add(myTaskBean);
        TaskListAdapter taskListAdapter=new TaskListAdapter(new WeakReference(MyApplication.getContext()),myTaskBeans);
        myTaskrv.setAdapter(taskListAdapter);
        taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String action;
                Intent intent=new Intent(MyTaskActivity.this,MyTaskDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

}
