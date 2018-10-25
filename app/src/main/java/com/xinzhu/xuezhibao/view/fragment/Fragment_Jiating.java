package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoCourseAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiatingCourseAdapter;
import com.xinzhu.xuezhibao.bean.JiaojiaoCourseBean;
import com.xinzhu.xuezhibao.bean.JiatingCourseBean;
import com.zou.fastlibrary.ui.spinner.NiceSpinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment_Jiating extends LazyLoadFragment {
    @BindView(R.id.sp_paixu)
    NiceSpinner spPaixu;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.rv_jiating)
    RecyclerView rvJiating;
    Unbinder unbinder;
    RvJiatingCourseAdapter adapter;
    WeakReference<Context> mContext;
    @Override
    protected int setContentView() {
        return R.layout.fragment_jiating;
    }

    @Override
    protected void lazyLoad() {
        mContext=new WeakReference(getActivity());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvJiating.setLayoutManager(linearLayoutManager3);
        initdata();
        spPaixu.setTextColor(Color.GREEN);
        LinkedList<String> data=new LinkedList<>(Arrays.asList("Zhang", "Phil", "@", "CSDN"));
        spPaixu.attachDataSource(data);
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
        unbinder.unbind();
    }

    public void initdata() {

        List<JiatingCourseBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        JiatingCourseBean itemBean = new JiatingCourseBean("12123165",url, title, "2235","主讲老师：搜索", "注意力训练");
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
        adapter = new RvJiatingCourseAdapter(mContext, list);
        rvJiating.setAdapter(adapter);
    }
}
