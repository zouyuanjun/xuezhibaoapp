package com.xinzhu.xuezhibao.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bravin.btoast.BToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.LoginActivity;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.VoiceDetilsActivity;
import com.xinzhu.xuezhibao.view.interfaces.HomeVideoVoiceListInterface;
import com.zou.fastlibrary.ui.CustomDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 主页中的视频和音频分类列表
 */
public class HomeVideoVoiceListFragment extends LazyLoadFragment implements HomeVideoVoiceListInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    VideoVoiceListAdapter adapter;
    Unbinder unbinder;
    static String TITLE = "type";
    int TYPE = -2;   //1,视频，2音频
    int POSITION = -1;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter homeVideoVoiceListPresenter;
    List<VideoVoiceBean> list = new ArrayList<>();
    int page = 1;
    boolean isfirstload = true;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    public static HomeVideoVoiceListFragment newInstance(int type) {
        HomeVideoVoiceListFragment tabFragment = new HomeVideoVoiceListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void lazyLoad() {
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loaddata(true);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loaddata(false);
                refreshlayout.finishLoadMore(2000);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        loaddata(true);
    }
    public void loaddata(boolean isfirstload) {
        if (isfirstload) {
            page = 1;
            list.clear();
            adapter.notifyDataSetChanged();
        }
        if (TYPE == 1) {
            if (POSITION == 0) {
                homeVideoVoiceListPresenter.getHotVideo(page);
            } else if (POSITION == 1) {
                homeVideoVoiceListPresenter.getNewVideo(page);
            } else if (POSITION == 2) {
                if (null == Constants.TOKEN || Constants.TOKEN.isEmpty()) {
                    showdia();
                    BToast.error(getContext()).text("请登陆后再查看").show();
                } else {
                    homeVideoVoiceListPresenter.getLikeVideo(page);
                }

            }
        } else if (TYPE == 2) {
            if (POSITION == 0) {
                homeVideoVoiceListPresenter.getHotVoice(page);
            } else if (POSITION == 1) {
                homeVideoVoiceListPresenter.getNewVoice(page);
            } else if (POSITION == 2) {
                if (null == Constants.TOKEN || Constants.TOKEN.isEmpty()) {
                    showdia();
                    BToast.error(getContext()).text("请登陆后再查看").show();
                } else {
                    homeVideoVoiceListPresenter.getLikeVoice(page);
                }

            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeVideoVoiceListPresenter = new VideoVoiceListPresenter(this);
        adapter = new VideoVoiceListAdapter(new WeakReference(MyApplication.getContext()), list);
        adapter.setOnItemClickListener(new VideoVoiceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                if (TYPE == 1) {
                    intent = new Intent(getContext(), VideoDetilsActivity.class);
                    intent.putExtra(Constants.INTENT_ID, list.get(position).getVideoId());
                } else if (TYPE == 2) {
                    intent = new Intent(getContext(), VoiceDetilsActivity.class);
                    intent.putExtra(Constants.INTENT_ID, list.get(position).getVideoId());
                }
                intent.putExtra(Constants.INTENT_ID, list.get(position).getVideoId());
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isfirstload = true;
        if (getArguments() != null) {
            TYPE = getArguments().getInt("TYPE");
            POSITION = getArguments().getInt("POSITION");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvItem.setAdapter(null);
        unbinder.unbind();
    }

    @Override
    public void getVideo(List<VideoVoiceBean> videoVoiceBeanList) {
        list.addAll(videoVoiceBeanList);
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        page++;
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void nodata() {
        refreshLayout.finishRefresh();
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMoreWithNoMoreData();
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
    public void showdia() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage("登陆后才能继续，现在登陆?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra(Constants.FROMAPP, "fss");
                startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
