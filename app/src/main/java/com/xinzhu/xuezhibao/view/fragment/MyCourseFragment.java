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
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.MyCourseFeedBackActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的标签里的我的课程fragment，包含家教课程，学科课程
 */
public class MyCourseFragment extends LazyLoadFragment implements MyCourseInterface {
    int POSITION = 0;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    RvJiaojiaoCourseAdapter rvJiaojiaoCourseAdapter;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    WeakReference<Context> mContext;
    MyCoursePresenter myCoursePresenter;
    List<CourseBean> courseBeanList = new ArrayList<>();
    int page = 1;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }
    @Override
    protected void lazyLoad() {
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(rvJiaojiaoCourseAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                courseBeanList.clear();
                page=1;
                if (POSITION == 0) {
                    myCoursePresenter.mygetcourse(page);
                } else {
                    myCoursePresenter.mygetcourse(page);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (POSITION == 0) {
                    myCoursePresenter.mygetcourse(page);
                } else {
                    myCoursePresenter.mygetcourse(page);
                }

            }
        });
        rvJiaojiaoCourseAdapter.notifyDataSetChanged();
        rvJiaojiaoCourseAdapter.setOnItemClickListener(new RvJiaojiaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(),CourseDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID,courseBeanList.get(position).getCurriculumId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void feedBackClick(View view, int position) {
                Intent intent = new Intent(getContext(), MyCourseFeedBackActivity.class);
                intent.putExtra(Constants.INTENT_ID, courseBeanList.get(position).getCurriculumId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = 1;
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
        myCoursePresenter = new MyCoursePresenter(this);
        if (POSITION == 0) {
            myCoursePresenter.mygetcourse(page);
        } else {
            myCoursePresenter.mygetcourse(page);
        }
        mContext = new WeakReference(MyApplication.getContext());
        rvJiaojiaoCourseAdapter = new RvJiaojiaoCourseAdapter(mContext, courseBeanList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvItem.setAdapter(null);
        unbinder.unbind();
    }


    @Override
    public void getcourse(List<CourseBean> courseList) {
        courseBeanList.addAll(courseList);
        if (null != rvJiaojiaoCourseAdapter) {
            rvJiaojiaoCourseAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        page++;
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void nodata() {
        refreshLayout.finishLoadMore();
        if (courseBeanList.size()==0){
            imDataisnull.setVisibility(View.VISIBLE);
        }
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void getTeacher(List<TeacherBean> mDatas) {

    }

    @Override
    public void getCourseFeesback(List<CourseFeedbackBean> mDatas, String unread) {

    }

    @Override
    public void getMyjob(List<MyjobBean> mDatas) {

    }
}
