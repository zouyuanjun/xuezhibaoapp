package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.view.activity.VideoActivity;
import com.zou.fastlibrary.activity.LazyLoadFragment;
import com.zou.fastlibrary.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_1 extends LazyLoadFragment {
    @BindView(R.id.textView2)
    TextView textView2;
    Unbinder unbinder;

    @Override
    protected int setContentView() {
        return R.layout.fragment_1;
    }

    @Override
    protected void lazyLoad() {
        Log.d("第一个Fragment可见了");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.textView2)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
    }
}