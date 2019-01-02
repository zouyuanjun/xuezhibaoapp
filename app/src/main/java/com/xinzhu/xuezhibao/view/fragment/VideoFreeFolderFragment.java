package com.xinzhu.xuezhibao.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoFolderAdapter;
import com.xinzhu.xuezhibao.adapter.VideoFreeFolderAdapter;
import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.xinzhu.xuezhibao.messagebean.PayResultMessage;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.VideoListActivity;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoFolderInterface;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 精品视频文件夹分类
 */
public class VideoFreeFolderFragment extends LazyLoadFragment implements VideoFolderInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    VideoFreeFolderAdapter payadapter;
    Unbinder unbinder;
    List<VideoFolder> payBeanList = new ArrayList<>();
    int paypage = 1;
    @BindView(R.id.rv_item)
    RecyclerView rvVideocourselist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter videoVoiceListPresenter;
    @BindView(R.id.img_nodata)
    ImageView imgNodata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_homevideocourse;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        paypage=1;
        payBeanList.clear();
        loaddata();
    }

    @Override
    protected void lazyLoad() {

    }

    private void loaddata() {
        videoVoiceListPresenter.getVideoFolder(paypage, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        appbar.setLeftImageVisible(false);
        videoVoiceListPresenter = new VideoVoiceListPresenter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        payadapter = new VideoFreeFolderAdapter( payBeanList);
        rvVideocourselist.setAdapter(payadapter);
        payadapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                payBeanList.clear();
                paypage = 1;
                loaddata();
                refreshLayout.finishRefresh(2000);
            }
        });

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
                intent.putExtra(Constants.INTENT_ID3, 1);
                startActivity(intent);
            }
        });
        appbar.setVisibility(View.GONE);
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
