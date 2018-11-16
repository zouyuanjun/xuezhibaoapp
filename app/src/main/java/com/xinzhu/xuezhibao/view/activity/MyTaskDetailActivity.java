package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.xinzhu.xuezhibao.presenter.TaskPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.GetTaskInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.WebViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskDetailActivity extends BaseActivity implements GetTaskInterface{
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_tasktitle)
    TextView tvTasktitle;
    @BindView(R.id.tv_taskdetail)
    WebView webTaskdetail;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.tv_taskstatu)
    ShapeCornerBgView tvTaskstatu;
    MyTaskBean myTaskBean;
    TaskPresenter taskPresenter;
    String taskid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytaskdetail);
        ButterKnife.bind(this);
        myTaskBean= (MyTaskBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        tvJifen.setText(myTaskBean.getAwardIntegral()+"");
        tvTasktitle.setText(myTaskBean.getTaskTitle());
        taskPresenter=new TaskPresenter(this);
        if (myTaskBean.getStateType()==0){
            myTaskBean.setStateType(100);
        }
        taskPresenter.gettaskdetail(myTaskBean.getTaskId(),myTaskBean.getStateType(),myTaskBean.getMyTaskId());
       if (myTaskBean.getStateType()==1){
            tvTaskstatu.setText("未完成");
        }else if (myTaskBean.getStateType()==2){
            tvTaskstatu.setText("已完成");
        } else{
            tvTaskstatu.setText("领取任务");
        }
        webTaskdetail.setWebViewClient(new WebViewUtil.MyWebViewClient(this,webTaskdetail));
    }

    @OnClick(R.id.tv_taskstatu)
    public void onViewClicked() {
        if (myTaskBean.getStateType()==1){
          return;
        }else if (myTaskBean.getStateType()==2){
           return;
        } else{
            taskPresenter.gettask(myTaskBean.getTaskId());
        }

    }

    @Override
    public void gettaskdetails(MyTaskBean myTaskBean) {
        webTaskdetail.loadDataWithBaseURL( null, myTaskBean.getTaskContent() , "text/html", "UTF-8", null ) ;
    }

    @Override
    public void accepttask() {
        BToast.success(this).text("领取成功").show();
        Intent intent=new Intent();
        intent.putExtra(Constants.INTENT_ID,1);
        setResult(1,intent);
        finish();
    }

    @Override
    public void gettaskfall() {

    }
}
