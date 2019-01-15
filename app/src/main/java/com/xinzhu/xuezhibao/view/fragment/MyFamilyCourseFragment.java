package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bravin.btoast.BToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CourseJobFoledAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoCourseAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoFeedbackAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTeacherAdapter;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.MessageNum;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.immodule.JGApplication;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.CourseFeedbackActivity;
import com.xinzhu.xuezhibao.view.activity.MyCourseFeedBackActivity;
import com.xinzhu.xuezhibao.view.activity.MyJobListActivity;
import com.xinzhu.xuezhibao.view.activity.TeacherDetailActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;
import com.zou.fastlibrary.utils.Log;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;

public class MyFamilyCourseFragment extends LazyLoadFragment implements MyCourseInterface {
    public static String TITLE = "CLASS";
    int MYCLASS = 0;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    RvJiaojiaoCourseAdapter rvJiaojiaoCourseAdapter;
    RvJiaojiaoTeacherAdapter rvJiaojiaoTeacherAdapter;
    CourseJobFoledAdapter rvJiaojiaoTaskAdapter;
    RvJiaojiaoFeedbackAdapter rvJiaojiaoFeedbackAdapter;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    WeakReference<Context> mContext;
    List<CourseBean> courseBeanArrayList = new ArrayList<>();
    List<TeacherBean> teacherBeanArrayList = new ArrayList<>();
    List<CourseBean> taskBeanArrayList = new ArrayList<>();
    List<CourseFeedbackBean> feedbackBeanArrayList = new ArrayList<>();
    MyCoursePresenter myCoursePresenter;
    int page = 1;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    boolean isfirstload = true;
    @BindView(R.id.im_loading)
    ImageView imLoading;

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
        Log.d("lazyLoad>>>>>>>>>>>>>>>"+MYCLASS);
        if (MYCLASS == 1) {
            rvItem.setAdapter(rvJiaojiaoCourseAdapter);
        } else if (MYCLASS == 2) {
            rvItem.setAdapter(rvJiaojiaoTeacherAdapter);
        } else if (MYCLASS == 3) {
            rvItem.setAdapter(rvJiaojiaoTaskAdapter);
        } else if (MYCLASS == 4) {
            rvItem.setAdapter(rvJiaojiaoFeedbackAdapter);
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loaddata(true);
                refreshLayout.finishRefresh(3000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loaddata(false);
                refreshLayout.finishLoadMore(3000);
            }
        });
        rvJiaojiaoCourseAdapter.setOnItemClickListener(new RvJiaojiaoCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (courseBeanArrayList.get(position).getIsApply()==1) {
                    Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                    intent.putExtra(Constants.INTENT_ID, courseBeanArrayList.get(position).getCurriculumId());
                    startActivity(intent);
                }
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
        rvJiaojiaoTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), MyJobListActivity.class);
                intent.putExtra(Constants.INTENT_ID, taskBeanArrayList.get(position).getCurriculumId());
                intent.putExtra(Constants.INTENT_ID2, taskBeanArrayList.get(position).getCurriculumTitle());
                intent.putExtra(Constants.INTENT_ID3, taskBeanArrayList.get(position).getCount());
                intent.putExtra("TYPE", 2);
                startActivity(intent);
            }
        }) ;
        rvJiaojiaoTeacherAdapter.setOnItemClickListener(new RvJiaojiaoTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, teacherBeanArrayList.get(position).getUserId());
                startActivity(intent);
            }

            @Override
            public void onImTalkClick(View view, int position) {
                if (null == JMessageClient.getMyInfo()){
                    BToast.error(getContext()).text("聊天服务异常，请退出重试或电话联系我们").show();
                    return;
                }
                Intent notificationIntent = new Intent(getActivity(), ChatActivity.class);
                notificationIntent.putExtra(JGApplication.TARGET_ID, teacherBeanArrayList.get(position).getMainPhone());
                notificationIntent.putExtra(JGApplication.CONV_TITLE, teacherBeanArrayList.get(position).getRealName() + "老师");
                notificationIntent.putExtra(JGApplication.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
                startActivity(notificationIntent);//自定义跳转到指定页面

            }
        });
        rvJiaojiaoFeedbackAdapter.setOnItemClickListener(new RvJiaojiaoFeedbackAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CourseFeedbackActivity.class);
                intent.putExtra(Constants.INTENT_ID, feedbackBeanArrayList.get(position));
                myCoursePresenter.replyfeedback(feedbackBeanArrayList.get(position).getFeedbackId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = 1;
        isfirstload = true;
        if (getArguments() != null) {
            MYCLASS = getArguments().getInt(TITLE);
        }
        myCoursePresenter = new MyCoursePresenter(this);
        mContext = new WeakReference(MyApplication.getContext());
        rvJiaojiaoTaskAdapter = new CourseJobFoledAdapter( taskBeanArrayList);
        rvJiaojiaoCourseAdapter = new RvJiaojiaoCourseAdapter(mContext, courseBeanArrayList);
        rvJiaojiaoTeacherAdapter = new RvJiaojiaoTeacherAdapter(mContext.get(), teacherBeanArrayList);
        rvJiaojiaoFeedbackAdapter = new RvJiaojiaoFeedbackAdapter(mContext.get(), feedbackBeanArrayList);

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume>>>>>>>>>>>>>>>"+MYCLASS);

        loaddata(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (teacherBeanArrayList.size() > 0 || taskBeanArrayList.size() > 0 || courseBeanArrayList.size() > 0 || feedbackBeanArrayList.size() > 0) {
            imDataisnull.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        myCoursePresenter.cancelmessage();
        super.onDestroyView();
        unbinder.unbind();
        page = 1;
        feedbackBeanArrayList.clear();
        courseBeanArrayList.clear();
        taskBeanArrayList.clear();
        teacherBeanArrayList.clear();
        isfirstload = true;
    }

    public void loaddata(boolean isfrist) {
        if (isfrist) {
            AnimationDrawable animationDrawable = (AnimationDrawable) imLoading.getDrawable();
            animationDrawable.start();
            imLoading.setVisibility(View.VISIBLE);
            feedbackBeanArrayList.clear();
            courseBeanArrayList.clear();
            taskBeanArrayList.clear();
            teacherBeanArrayList.clear();
            page = 1;
        }
        if (MYCLASS == 1) {
            myCoursePresenter.getcourse(page, 1);
        } else if (MYCLASS == 2) {
            myCoursePresenter.getTeacher(page, 1);
        } else if (MYCLASS == 3) {
            myCoursePresenter.getjobfoled(page, 1);
        } else if (MYCLASS == 4) {
            myCoursePresenter.getcoursefeedback(page, 1);
        }
    }

    @Override
    public void getcourse(List<CourseBean> courseBeanList) {
        courseBeanArrayList.addAll(courseBeanList);
        if (null != rvJiaojiaoCourseAdapter && null != refreshLayout) {
            rvJiaojiaoCourseAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
            imLoading.setVisibility(View.GONE);
            imDataisnull.setVisibility(View.GONE);
        }


    }

    @Override
    public void nodata() {
        if (null==refreshLayout){
            return;
        }
        refreshLayout.finishLoadMoreWithNoMoreData();
        refreshLayout.finishRefresh();
        if (MYCLASS == 1 && courseBeanArrayList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
        } else if (MYCLASS == 2 && teacherBeanArrayList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
        } else if (MYCLASS == 3 && taskBeanArrayList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
        } else if (MYCLASS == 4 && feedbackBeanArrayList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
        }
        imLoading.setVisibility(View.GONE);

    }

    @Override
    public void getTeacher(List<TeacherBean> mDatas) {
        teacherBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoTeacherAdapter && null != refreshLayout) {
            rvJiaojiaoTeacherAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
            imLoading.setVisibility(View.GONE);
            imDataisnull.setVisibility(View.GONE);
        }

    }

    @Override
    public void getCourseFeesback(List<CourseFeedbackBean> mDatas, String unread) {
        feedbackBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoFeedbackAdapter && null != refreshLayout) {
            rvJiaojiaoFeedbackAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
            imLoading.setVisibility(View.GONE);
            imDataisnull.setVisibility(View.GONE);
        }

        EventBus.getDefault().post(new MessageNum(unread));

    }

    @Override
    public void getMyjob(List<CourseBean> mDatas) {
        taskBeanArrayList.addAll(mDatas);
        if (null != rvJiaojiaoTaskAdapter && null != refreshLayout) {
            rvJiaojiaoTaskAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
            imLoading.setVisibility(View.GONE);
            imDataisnull.setVisibility(View.GONE);
        }

    }
}
