package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ArticleListAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiatingCourseAdapter;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.MyCollectPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.ArticleDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.VoiceDetilsActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyCollectInterface;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的收藏页面1，课程收藏，2 文章，3 音频 ，4视频
 */
public class MyCollectFragment extends LazyLoadFragment implements MyCollectInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    int POSITION = 0;
    RvJiatingCourseAdapter courseMyCollectAdapter;
    ArticleListAdapter articleListAdapter;
    VideoVoiceListAdapter videoVoiceListAdapter;
    List<CourseBean> courseBeanList = new ArrayList<>();
    List<ArticleBean> articleBeanList = new ArrayList<>();
    List<VideoVoiceBean> videoVoiceBeanList = new ArrayList<>();
    boolean isfirstload = true;
    MyCollectPresenter myCollectPresenter;
    int page = 1;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    @BindView(R.id.im_loading)
    ImageView imLoading;
boolean nodata=false;
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
          if (nodata){
            imDataisnull.setVisibility(View.VISIBLE);
        }
        if (isfirstload) {
            isfirstload = false;
            imLoading.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) imLoading.getDrawable();
            drawable.start();
            if (POSITION == 0) {
                rvItem.setAdapter(courseMyCollectAdapter);
                myCollectPresenter.getCollectCourse(page);
            } else if (POSITION == 1) {
                rvItem.setAdapter(articleListAdapter);
                myCollectPresenter.getCollectVAreticle(page);
            } else if (POSITION == 2) {
                rvItem.setAdapter(videoVoiceListAdapter);
                myCollectPresenter.getCollectVoice(page);
            } else if (POSITION == 3) {
                rvItem.setAdapter(videoVoiceListAdapter);
                myCollectPresenter.getCollectVideo(page);
            }
        }

        courseMyCollectAdapter.setOnItemClickListener(new RvJiatingCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, courseBeanList.get(position).getCurriculumId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ArticleDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID, articleBeanList.get(position).getArticleId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        videoVoiceListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent;
                if (POSITION == 2) {
                    intent = new Intent(getContext(), VoiceDetilsActivity.class);
                } else {
                    intent = new Intent(getContext(), VideoDetilsActivity.class);
                }
                intent.putExtra(Constants.INTENT_ID, videoVoiceBeanList.get(position).getVideoId());
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                courseBeanList.clear();
                videoVoiceBeanList.clear();
                articleBeanList.clear();
                page = 1;
                if (POSITION == 0) {
                    myCollectPresenter.getCollectCourse(page);
                } else if (POSITION == 1) {
                    myCollectPresenter.getCollectVAreticle(page);
                } else if (POSITION == 2) {
                    myCollectPresenter.getCollectVoice(page);
                } else if (POSITION == 3) {
                    myCollectPresenter.getCollectVideo(page);
                }


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    myCollectPresenter.getCollectCourse(page);
                } else if (POSITION == 1) {
                    myCollectPresenter.getCollectVAreticle(page);
                } else if (POSITION == 2) {
                    myCollectPresenter.getCollectVoice(page);
                } else if (POSITION == 3) {
                    myCollectPresenter.getCollectVideo(page);
                }

            }
        });

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
        Log.d("收餐"+POSITION);
        courseMyCollectAdapter = new RvJiatingCourseAdapter(new WeakReference<Context>(getActivity()), courseBeanList);
        myCollectPresenter = new MyCollectPresenter(this);
        articleListAdapter = new ArticleListAdapter(new WeakReference<Context>(getActivity()), articleBeanList);
        videoVoiceListAdapter = new VideoVoiceListAdapter(new WeakReference<Context>(getActivity()), videoVoiceBeanList);
        page = 1;
        isfirstload = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager);
        if (POSITION == 0) {
            rvItem.setAdapter(courseMyCollectAdapter);
        } else if (POSITION == 1) {
            rvItem.setAdapter(articleListAdapter);
        } else if (POSITION == 2) {
            rvItem.setAdapter(videoVoiceListAdapter);
        } else if (POSITION == 3) {
            rvItem.setAdapter(videoVoiceListAdapter);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void getvideo(List<VideoVoiceBean> BeanList) {
        videoVoiceBeanList.addAll(BeanList);
        videoVoiceListAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        page++;
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void getvoice(List<VideoVoiceBean> BeanList) {
        videoVoiceBeanList.addAll(BeanList);
        videoVoiceListAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        page++;
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void getcourse(List<CourseBean> BeanList) {
        courseBeanList.addAll(BeanList);
        courseMyCollectAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        page++;
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void getarticle(List<ArticleBean> BeanList) {
        articleBeanList.addAll(BeanList);
        articleListAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        page++;
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void nodata() {
        articleListAdapter.notifyDataSetChanged();
        courseMyCollectAdapter.notifyDataSetChanged();
        videoVoiceListAdapter.notifyDataSetChanged();
        imLoading.setVisibility(View.GONE);
        refreshLayout.finishLoadMoreWithNoMoreData();
        refreshLayout.finishRefresh(false);
        if (courseBeanList.size() == 0 && videoVoiceBeanList.size() == 0 && articleBeanList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
            nodata=true;
        }


    }
}
