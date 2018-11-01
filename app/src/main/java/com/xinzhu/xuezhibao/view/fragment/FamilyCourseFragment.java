package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvJiatingCourseAdapter;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.JiatingCourseBean;
import com.xinzhu.xuezhibao.presenter.CoursePresenter;
import com.xinzhu.xuezhibao.view.interfaces.FamilyCourseInterface;
import com.zou.fastlibrary.ui.spinner.NiceSpinner;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 主页学宝课程里的家庭教育页面
 */
public class FamilyCourseFragment extends LazyLoadFragment implements FamilyCourseInterface {
    @BindView(R.id.sp_paixu)
    NiceSpinner spPaixu;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.rv_jiating)
    RecyclerView rvJiating;
    Unbinder unbinder;
    RvJiatingCourseAdapter adapter;
    WeakReference<Context> mContext;
    int page=1;
    CoursePresenter coursePresenter;
    int TYPE=0;
    @Override
    protected int setContentView() {
        return R.layout.fragment_jiating;
    }
    List<CourseBean>  beanList=new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getInt("TYPE");
        }
    }
    @Override
    protected void lazyLoad() {
        coursePresenter=new CoursePresenter(this);
        mContext=new WeakReference(getActivity());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvJiating.setLayoutManager(linearLayoutManager3);
        adapter = new RvJiatingCourseAdapter(mContext, beanList);
        rvJiating.setAdapter(adapter);
        LinkedList<String> data=new LinkedList<>(Arrays.asList("综合排序", "人气优先"));
        spPaixu.attachDataSource(data);
        spPaixu.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("选择了"+i);
            }
        });
        if (TYPE==1){
            coursePresenter.getFamilyHotCourse(page);
        }else if (TYPE==2){
            coursePresenter.getFamilyNewCourse(page);
        }else if (TYPE==3){
            coursePresenter.getFamilyRecommendCourse(page);
        }else if (TYPE==4){
            coursePresenter.getFamilyCourse(page);
        }

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
        page=1;
        rvJiating.setAdapter(null);
        unbinder.unbind();
    }

    @Override
    public void getFamilyCourse(List<CourseBean> list) {
        beanList.addAll(list);
        adapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void getFamilyHotCourse(List<CourseBean> list) {

    }

    @Override
    public void searchFamilyCourse(List<CourseBean> list) {

    }
}
