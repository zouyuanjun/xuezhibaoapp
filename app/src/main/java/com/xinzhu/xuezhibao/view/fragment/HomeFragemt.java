package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.view.custom.BadgeView;
import com.zou.fastlibrary.activity.LazyLoadFragment;
import com.zou.fastlibrary.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.tasks.GetEventNotificationTaskMng;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragemt extends LazyLoadFragment {
    @BindView(R.id.bt_m1)
    ImageView btM1;
    @BindView(R.id.bt_m2)
    ImageView btM2;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.tv_message)
    TextView tvmessage;
int messagecount=0;
    Unbinder unbinder;
    Fragment_1 f1;
    Fragment_2 f2;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    QBadgeView qBadgeView;
    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        initFragment1();
        fragmentManager=getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Log.d("懒加载方法可见");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        qBadgeView=new QBadgeView(getActivity());
        qBadgeView.bindTarget(tvmessage).setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        Log.d("创建完毕");
        JMessageClient.registerEventReceiver(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_m1, R.id.bt_m2, R.id.fragment,R.id.tv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_m1:
                initFragment1();

                break;
            case R.id.bt_m2:
                initFragment2();

                break;
            case R.id.fragment:
                break;
            case R.id.tv_message:
                getActivity().startActivity(new Intent( getContext(), ConversationListActivity.class));
                break;
        }
    }

   //显示第一个fragment
    private void initFragment1(){
        Log.d("显示第一个");
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
//        if(f1 == null){
//            f1 = new Fragment_1();
//            transaction.add(R.id.fragment, f1);
//        }
//        //隐藏所有fragment
//        hideFragment(transaction);
//        //显示需要显示的fragment
//        transaction.show(f1);

        //第二种方式(replace)，初始化fragment
        if(f1 == null){
            f1 = new Fragment_1();
        }
        transaction.replace(R.id.fragment, f1);

        //提交事务
        transaction.commit();
    }
    //显示第一个fragment
    private void initFragment2(){
        Log.d("显示第二个");
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
//        if(f2 == null){
//            f2 = new Fragment_2();
//            transaction.add(R.id.fragment, f2);
//        }
//        //隐藏所有fragment
//      hideFragment(transaction);
//        //显示需要显示的fragment
//        transaction.show(f2);

        //第二种方式(replace)，初始化fragment
        if(f2 == null){
            f2 = new Fragment_2();
        }
        transaction.replace(R.id.fragment, f2);

        //提交事务
        transaction.commit();
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(f1 != null){
            transaction.hide(f1);
        }
        if(f2 != null){
            transaction.hide(f2);
        }
    }
    public void onEvent(MessageEvent event) {
      //  event.getMessage().toJson();
        messagecount++;
        qBadgeView.setBadgeNumber(messagecount);
        Log.d("收到l一条消息"+messagecount);
    }

}
