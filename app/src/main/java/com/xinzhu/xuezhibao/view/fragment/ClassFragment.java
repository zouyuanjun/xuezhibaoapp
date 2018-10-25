package com.xinzhu.xuezhibao.view.fragment;

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
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ArticleListAdapter;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.ItemBean;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClassFragment extends LazyLoadFragment {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    VideoVoiceListAdapter adapter;
    Unbinder unbinder;
    static String TITLE="type";
    int TYPE=-2;
    int POSITION=-1;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int setContentView() {
        return R.layout.fragment_class;
    }
    public static ClassFragment newInstance(int type) {
        ClassFragment tabFragment = new ClassFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    @Override
    protected void lazyLoad() {
        adapter = new VideoVoiceListAdapter(new WeakReference(getContext()), initdata());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(adapter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getInt("TYPE");
            POSITION=getArguments().getInt("POSITION");
        }
        Log.d("类型"+TYPE+POSITION);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public List<ItemBean> initdata() {
        List<ItemBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        ItemBean itemBean = new ItemBean(url, title, "225", "2553", "2018.254", 1);
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
        return list;
    }
}
