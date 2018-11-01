package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvXuekeCourseAdapter;
import com.xinzhu.xuezhibao.bean.XuekeCourseBean;
import com.zou.fastlibrary.ui.spinner.NiceSpinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 学宝页面全部课程里的学科页面
 */
public class FragmentXuekeCourse extends LazyLoadFragment {

    Unbinder unbinder;
    RvXuekeCourseAdapter adapter;
    WeakReference<Context> mContext;
    @BindView(R.id.sp_zonghe)
    NiceSpinner spZonghe;
    @BindView(R.id.sp_nianji)
    NiceSpinner spNianji;
    @BindView(R.id.sp_kemu)
    NiceSpinner spKemu;
    @BindView(R.id.rv_xueke)
    RecyclerView rvXueke;

    @Override
    protected int setContentView() {
        return R.layout.fragment_xueke;
    }

    @Override
    protected void lazyLoad() {
        mContext = new WeakReference(MyApplication.getContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvXueke.setLayoutManager(linearLayoutManager3);
        initdata();
        LinkedList<String> data=new LinkedList<>(Arrays.asList("综合排序", "人气优先"));
        spZonghe.attachDataSource(data);
        LinkedList<String> data2=new LinkedList<>(Arrays.asList("一年级", "二年级","三年级","四年级","五年级","6年纪"));
        spNianji.attachDataSource(data2);
        LinkedList<String> data3=new LinkedList<>(Arrays.asList("数学", "语文"));
        spKemu.attachDataSource(data3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvXueke.setAdapter(null);
        unbinder.unbind();
    }

    public void initdata() {

        List<XuekeCourseBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        XuekeCourseBean itemBean = new XuekeCourseBean("12123165", url, title, "2235", "主讲老师：搜索", "一年级", "语文");
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
        adapter = new RvXuekeCourseAdapter(mContext, list);
        rvXueke.setAdapter(adapter);
    }
}
