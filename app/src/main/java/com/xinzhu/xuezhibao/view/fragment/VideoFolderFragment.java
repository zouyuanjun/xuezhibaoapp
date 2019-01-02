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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoFolderAdapter;
import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.xinzhu.xuezhibao.messagebean.PayResultMessage;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
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
public class VideoFolderFragment extends LazyLoadFragment implements VideoFolderInterface, PayInterface {
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
    AlipayPresenter alipayPresenter;
    boolean isfirstload = true;
    @BindView(R.id.img_nodata)
    ImageView imgNodata;
    int POSITION;
    PopupWindow loadingPop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
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
        videoVoiceListPresenter.getVideoFolder(paypage, 1);
//        if (isfirstload) {
//            if (POSITION == 2) {
//                videoVoiceListPresenter.getVideoFolder(paypage, 0);
//            } else {
//
//            }
//            isfirstload = false;
//        } else {
//            if (POSITION == 2) {
//                videoVoiceListPresenter.getVideoFolder(paypage, 0);
//            } else {
//                videoVoiceListPresenter.getVideoFolder(paypage, 1);
//            }
//
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        appbar.setLeftImageVisible(false);
        videoVoiceListPresenter = new VideoVoiceListPresenter(this);
        alipayPresenter = new AlipayPresenter(this, getActivity());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        payadapter = new VideoFolderAdapter(R.layout.item_video_folder, payBeanList,1);
//        if (POSITION==2){ //免费视频
//            payadapter = new VideoFolderAdapter(R.layout.item_video_folder, payBeanList,0);
//        }else { //收费视频
//
//        }

        payadapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                if (view.getId() == R.id.shapeCornerBgView) {
                    if (payBeanList.get(position).getIsBuy()==1){
                        return;
                    }
                    final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(getActivity(), R.layout.pop_pay);
                    final View myview = popupWindow.getContentView();
                    RadioGroup radioGroup = myview.findViewById(R.id.rg_pay);
                    TextView textView = myview.findViewById(R.id.tv_cancle);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            switch (i) {
                                case R.id.rd_alipay:
                                    alipayPresenter.aLiBuyVideo(payBeanList.get(position).getVideoTypeId(), 1);
                                    popupWindow.dismiss();
                                    loadingPop = CreatPopwindows.creatWWpopwindows(getActivity(), R.layout.pop_loading);
                                    loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    break;
                                case R.id.rd_wxpay:
                                    alipayPresenter.WxBuyVideo(payBeanList.get(position).getVideoTypeId(), 1);
                                    popupWindow.dismiss();
                                    loadingPop = CreatPopwindows.creatWWpopwindows(getActivity(), R.layout.pop_loading);
                                    loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    break;
                            }
                        }
                    });

                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(getContext()));

                }
            }
        });
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
                if (POSITION==2){ //免费视频
                    intent.putExtra(Constants.INTENT_ID3, 1);
                }else { //收费视频
                    intent.putExtra(Constants.INTENT_ID3, 2);
                }
                startActivity(intent);
            }
        });
        if (POSITION == 2) {
            appbar.setVisibility(View.GONE);
        }
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

    @Override
    public void paysuccessful() {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.success(getContext()).text("购买成功").show();
    }

    @Override
    public void payfail() {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.success(getContext()).text("支付失败").show();
    }

    @Override
    public void orderisexit() {

    }

    @Override
    public void creatOrderfail(String tips) {

    }

    @Subscribe
    public void PayMessage(PayResultMessage messageEvent) {
        int code = messageEvent.getCode();
        if (code == 0) {
            if (StringUtil.isEmpty(Constants.wxOrdernum)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("支付结果");
                builder.setMessage("您可能支付成功，但是由于网络异常，我们无法获取支付结果，请联系客服人员为您核实");
                builder.show();
            } else {
                alipayPresenter.checkWxPay();
            }
        } else if (code == 1) {
            BToast.error(getContext()).text("取消支付").show();
        } else {
            if (null != loadingPop && loadingPop.isShowing()) {
                loadingPop.dismiss();
            }
            BToast.error(getContext()).text("微信支付异常").show();
        }
    }
}
