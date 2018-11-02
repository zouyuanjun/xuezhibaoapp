package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.view.activity.TestActivity;
import com.xinzhu.xuezhibao.view.activity.TestBeforeActivity;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TestFragment extends LazyLoadFragment {
    @BindView(R.id.begintest)
    ShapeCornerBgView begintest;
    Unbinder unbinder;

    @Override
    protected int setContentView() {
        return R.layout.fragment_test;
    }

    @Override
    protected void lazyLoad() {

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

    @OnClick(R.id.begintest)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getContext(),TestBeforeActivity.class));
    }
}
