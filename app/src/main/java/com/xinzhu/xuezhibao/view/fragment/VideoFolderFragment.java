package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoFolderAdapter;
import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.VideoListActivity;
import com.xinzhu.xuezhibao.view.interfaces.VideoFolderInterface;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 精品视频
 */
public class VideoFolderFragment extends LazyLoadFragment implements VideoFolderInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    VideoFolderAdapter payadapter;
    Unbinder unbinder;
    List<VideoFolder> payBeanList = new ArrayList<>();
    int paypage = 1;
    @BindView(R.id.rv_item)
    RecyclerView rvVideocourselist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter videoVoiceListPresenter;
    boolean isfirstload = true;
    @BindView(R.id.img_nodata)
    ImageView imgNodata;
    @Override
    protected int setContentView() {
        return R.layout.fragment_homevideocourse;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void lazyLoad() {

    }

    private void loaddata() {
        appbar.setLeftImageVisible(false);
        if (isfirstload) {
            videoVoiceListPresenter.getVideoFolder(1);
            isfirstload = false;
        } else {
            videoVoiceListPresenter.getVideoFolder(paypage);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        videoVoiceListPresenter = new VideoVoiceListPresenter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        payadapter = new VideoFolderAdapter(R.layout.item_video_folder, payBeanList);
        rvVideocourselist.setAdapter(payadapter);
        payadapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                payBeanList.clear();
                paypage = 1;
                loaddata();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loaddata();
            }
        });
        loaddata();
        payadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (StringUtil.isEmpty(Constants.TOKEN)) {
                    DialogUtils.loginDia(getActivity());
                    return;
                }
                String id = payBeanList.get(position).getVideoTypeId();

                Intent intent = new Intent(getContext(), VideoListActivity.class);
                intent.putExtra(Constants.INTENT_ID, id);
                intent.putExtra(Constants.INTENT_ID2, payBeanList.get(position).getVideoTypeName());
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void networktimeout() {
        super.networktimeout();
    }

    @Override
    public void networkerr() {
        super.networkerr();
    }

    @Override
    public void servererr() {
        super.servererr();
    }

    @Override
    public void getvideofolder(List<VideoFolder> videoFolderlist) {
        if (null != refreshLayout) {
            payBeanList.addAll(videoFolderlist);
            payadapter.notifyDataSetChanged();
            paypage++;
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh(true);
        }
    }

    @Override
    public void nomoredata() {
        if (null != refreshLayout) {
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
        if (payBeanList.size() == 0) {
            imgNodata.setVisibility(View.VISIBLE);
        }
    }
}
