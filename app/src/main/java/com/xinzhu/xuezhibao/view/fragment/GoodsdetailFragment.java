package com.xinzhu.xuezhibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.WebView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.WebViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoodsdetailFragment extends Fragment {
    String goodsBean;
    @BindView(R.id.web_goodsdetail)
    WebView webGoodsdetail;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsBean = getArguments().getString(Constants.INTENT_ID);
    }

    public GoodsdetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_gooddetailwebview, container, false);
        unbinder = ButterKnife.bind(this, view);
        webGoodsdetail.setWebViewClient(new WebViewUtil.MyWebViewClient(getActivity(), webGoodsdetail));
        webGoodsdetail.loadDataWithBaseURL(null, goodsBean, "text/html", "UTF-8", null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
