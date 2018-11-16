package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoCourseAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoFeedbackAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTaskAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTeacherAdapter;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.CourseFeedbackActivity;
import com.xinzhu.xuezhibao.view.activity.CourseTaskActivity;
import com.xinzhu.xuezhibao.view.activity.MyCourseFeedBackActivity;
import com.xinzhu.xuezhibao.view.activity.TeacherDetailActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFamilyCourseFragment extends LazyLoadFragment implements MyCourseInterface {
    public static String TITLE = "CLASS";
    int MYCLASS = 0;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    RvJiaojiaoCourseAdapter rvJiaojiaoCourseAdapter;
    RvJiaojiaoTeacherAdapter rvJiaojiaoTeacherAdapter;
    RvJiaojiaoTaskAdapter rvJiaojiaoTaskAdapter;
    RvJiaojiaoFeedbackAdapter rvJiaojiaoFeedbackAdapter;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    WeakReference<Context> mContext;
    List<CourseBean> courseBeanArrayList = new ArrayList<>();
    List<TeacherBean> teacherBeanArrayList = new ArrayList<>();
    List<MyjobBean> taskBeanArrayList = new ArrayList<>();
    List<CourseFeedbackBean> feedbackBeanArrayList = new ArrayList<>();
    MyCoursePresenter myCoursePresenter;
    int page = 1;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
boolean isfirstload=true;
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    public static MyFamilyCourseFragment newInstance(int type) {
        MyFamilyCourseFragment tabFragment = new MyFamilyCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void lazyLoad() {
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvJiaojiaoTaskAdapter = new RvJiaojiaoTaskAdapter(mContext.get(), taskBeanArrayList);
        rvJiaojiaoCourseAdapter = new RvJiaojiaoCourseAdapter(mContext, courseBeanArrayList);
        rvJiaojiaoTeacherAdapter = new RvJiaojiaoTeacherAdapter(mContext.get(), teacherBeanArrayList);
        rvJiaojiaoFeedbackAdapter = new RvJiaojiaoFeedbackAdapter(mContext.get(), feedbackBeanArrayList);
        if (isfirstload){
            isfirstload=false;
            if (MYCLASS == 1) {
                myCoursePresenter.getcourse(page, 1);
                rvItem.setAdapter(rvJiaojiaoCourseAdapter);
            } else if (MYCLASS == 2) {
                myCoursePresenter.getTeacher(page, 1);
                rvItem.setAdapter(rvJiaojiaoTeacherAdapter);

            } else if (MYCLASS == 3) {
                myCoursePresenter.getjob(page,1);
                rvItem.setAdapter(rvJiaojiaoTaskAdapter);

            } else if (MYCLASS == 4) {
                myCoursePresenter.getcoursefeedback(page, 1);
                rvItem.setAdapter(rvJiaojiaoFeedbackAdapter);
            }
        }



        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                courseBeanArrayList.clear();
                feedbackBeanArrayList.clear();
                taskBeanArrayList.clear();
                teacherBeanArrayList.clear();
                page=1;
                if (MYCLASS == 1) {
                    myCoursePresenter.getcourse(page, 1);
                } else if (MYCLASS == 2) {
                    myCoursePresenter.getTeacher(page, 1);
                } else if (MYCLASS == 3) {
                    myCoursePresenter.getjob(page,1);
                } else if (MYCLASS == 4) {
                    myCoursePresenter.getcoursefeedback(page, 1);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                if (MYCLASS == 1) {
                    myCoursePresenter.getcourse(page, 1);
                } else if (MYCLASS == 2) {
                    myCoursePresenter.getTeacher(page, 1);
                } else if (MYCLASS == 3) {
                    myCoursePresenter.getjob(page,1);
                } else if (MYCLASS == 4) {
                    myCoursePresenter.getcoursefeedback(page, 1);
                }
            }
        });
        rvJiaojiaoCourseAdapter.setOnItemClickListener(new RvJiaojiaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, courseBeanArrayList.get(position).getCurriculumId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void feedBackClick(View view, int position) {

                Intent intent = new Intent(getContext(), MyCourseFeedBackActivity.class);
                intent.putExtra(Constants.INTENT_ID, courseBeanArrayList.get(position).getCurriculumId());
                startActivity(intent);
            }
        });
        rvJiaojiaoTaskAdapter.setOnItemClickListener(new RvJiaojiaoTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CourseTaskActivity.class);
                intent.putExtra(Constants.INTENT_ID, taskBeanArrayList.get(position).getJobId());
                intent.putExtra("TYPE",2);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rvJiaojiaoTeacherAdapter.setOnItemClickListener(new RvJiaojiaoTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(), TeacherDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID,teacherBeanArrayList.get(position).getUserId());
                startActivity(intent);
            }

            @Override
            public void onImTalkClick(View view, int position) {

            }
        });
        rvJiaojiaoFeedbackAdapter.setOnItemClickListener(new RvJiaojiaoFeedbackAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(), CourseFeedbackActivity.class);
                intent.putExtra(Constants.INTENT_ID,feedbackBeanArrayList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MyFamailyCourseFragment到这里来了");
        super.onCreate(savedInstanceState);
        page = 1;
        isfirstload=true;
        if (getArguments() != null) {
            MYCLASS = getArguments().getInt(TITLE);
        }
        myCoursePresenter = new MyCoursePresenter(this);
        mContext = new WeakReference(MyApplication.getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (teacherBeanArrayList.size()>0||taskBeanArrayList.size()>0||courseBeanArrayList.size()>0||feedbackBeanArrayList.size()>0){
            imDataisnull.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        myCoursePresenter.cancelmessage();
        super.onDestroyView();
        unbinder.unbind();
        page=1;
        feedbackBeanArrayList.clear();
        courseBeanArrayList.clear();
        taskBeanArrayList.clear();
        teacherBeanArrayList.clear();
        isfirstload=true;
    }


    @Override
    public void getcourse(List<CourseBean> courseBeanList) {
        courseBeanArrayList.addAll(courseBeanList);
        if (null != rvJiaojiaoCourseAdapter&&null!=refreshLayout) {
            rvJiaojiaoCourseAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        page++;
        imDataisnull.setVisibility(View.GONE);

    }

    @Override
    public void nodata() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void getTeacher(List<TeacherBean> mDatas) {
        teacherBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoTeacherAdapter&&null!=refreshLayout) {
            rvJiaojiaoTeacherAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        page++;
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void getCourseFeesback(List<CourseFeedbackBean> mDatas, String unread) {
        feedbackBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoFeedbackAdapter&&null!=refreshLayout) {
            rvJiaojiaoFeedbackAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        page++;
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void getMyjob(List<MyjobBean> mDatas) {
        taskBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoTaskAdapter&&null!=refreshLayout) {
            rvJiaojiaoTaskAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        page++;
        imDataisnull.setVisibility(View.GONE);
    }
}
