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
import com.xinzhu.xuezhibao.bean.JiajiaoFeedbackBean;
import com.xinzhu.xuezhibao.bean.JiaojiaoCourseBean;
import com.xinzhu.xuezhibao.bean.TaskBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.view.activity.CourseFeedbackActivity;
import com.xinzhu.xuezhibao.view.activity.CourseTaskActivity;
import com.xinzhu.xuezhibao.view.activity.TeacherDetailActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class JiajiaoCourseFragment extends LazyLoadFragment {
    public  static String TITLE="CLASS";
    int MYCLASS=0;
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

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    public static JiajiaoCourseFragment newInstance(int type) {
        JiajiaoCourseFragment tabFragment = new JiajiaoCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void lazyLoad() {
        mContext=new WeakReference(MyApplication.getContext());
      if (MYCLASS==1){
          initdata();
      }else if (MYCLASS==2){
          initdata2();
      }else if (MYCLASS==3){
          initdata3();
      }else if (MYCLASS==4){
          initdata4();
      }
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MYCLASS = getArguments().getInt(TITLE);
        }
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


    public void initdata() {

        List<JiaojiaoCourseBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        JiaojiaoCourseBean itemBean = new JiaojiaoCourseBean(url, title, "225", "主讲老师：搜索", "41节/", "12");
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        rvJiaojiaoCourseAdapter = new RvJiaojiaoCourseAdapter(mContext, list);
        rvItem.setAdapter(rvJiaojiaoCourseAdapter);
    }
    public void initdata2() {
        List<TeacherBean> list = new ArrayList<>();
        String url = "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=0fd5d24f9f45d688bc02b4a494c27dab/4b90f603738da977f53a9d57bd51f8198618e3b1.jpg";
        String title = "主讲老师：十三点";
        TeacherBean itemBean = new TeacherBean(url, title, "孩子不听话怎么办");
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        rvJiaojiaoTeacherAdapter = new RvJiaojiaoTeacherAdapter(getContext(),list);
        rvItem.setAdapter(rvJiaojiaoTeacherAdapter);
        rvJiaojiaoTeacherAdapter.setOnItemClickListener(new RvJiaojiaoTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(),TeacherDetailActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
    public void initdata3() {
        List<TaskBean> list = new ArrayList<>();
        String url = "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=0fd5d24f9f45d688bc02b4a494c27dab/4b90f603738da977f53a9d57bd51f8198618e3b1.jpg";
        String title = "主讲老师：十三点";
        TaskBean itemBean = new TaskBean("215456",  "孩子不听话怎么办","2018:1552246","已完成");
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        rvJiaojiaoTaskAdapter = new RvJiaojiaoTaskAdapter(getContext(),list);
        rvItem.setAdapter(rvJiaojiaoTaskAdapter);
        rvJiaojiaoTaskAdapter.setOnItemClickListener(new RvJiaojiaoTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(),CourseTaskActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    public void initdata4() {
        List<JiajiaoFeedbackBean> list = new ArrayList<>();
        String url = "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=0fd5d24f9f45d688bc02b4a494c27dab/4b90f603738da977f53a9d57bd51f8198618e3b1.jpg";
        String title = "主讲老师：十三点";
        JiajiaoFeedbackBean itemBean = new JiajiaoFeedbackBean("215456",  "孩子不听话怎么办","您有意识到妇女is三年的覅哦啊是","2","关于洒家扩大飞机啊螺丝钉解放了喀什的发射点的反馈");
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        rvJiaojiaoFeedbackAdapter = new RvJiaojiaoFeedbackAdapter(getContext(),list);
        rvItem.setAdapter(rvJiaojiaoFeedbackAdapter);
        rvJiaojiaoFeedbackAdapter.setOnItemClickListener(new RvJiaojiaoFeedbackAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(),CourseFeedbackActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
