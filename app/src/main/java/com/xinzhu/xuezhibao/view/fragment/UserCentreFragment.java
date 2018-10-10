package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.view.activity.UserBaseActivity;
import com.zou.fastlibrary.activity.LazyLoadFragment;
import com.zou.fastlibrary.utils.CreatPopwindows;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserCentreFragment extends LazyLoadFragment {
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    Unbinder unbinder;

    @Override
    protected int setContentView() {
        return R.layout.fragment_usercentre;
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

    @OnClick(R.id.sd_myphoto)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), UserBaseActivity.class));
    }
}
