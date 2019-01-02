package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.TesttitleAdapter;
import com.xinzhu.xuezhibao.bean.TestBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestIntroduceActivity extends BaseActivity {

    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string = (String) msg.obj;
            Log.d(string);
            int code = JsonUtils.getIntValue(string, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(string, "Data");
                final List<TestBean> testBean = JSON.parseArray(data,TestBean.class);
                TesttitleAdapter testtitleAdapter=new TesttitleAdapter(testBean);
                recyclerview.setAdapter(testtitleAdapter);
                testtitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(TestIntroduceActivity.this, TestBeforeActivity.class);
                        intent.putExtra(Constants.INTENT_ID, position);
                        intent.putExtra(Constants.INTENT_ID2,testBean.get(position).getDictionaryId());
                        intent.putExtra(Constants.INTENT_ID3,testBean.get(position).getDictionaryName());
                        intent.putExtra(Constants.INTENT_ID4,testBean.get(position).getDictionaryDescribe());

                        startActivity(intent);
                    }
                });
            }
        }
    };
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testintroduce);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String data = JsonUtils.keyValueToString("dictionaryType", "appraisal_type");
        Network.getnetwork().postJson(data, Constants.URL + "/guest/page-by-dictionary", handler, 1);
    }
//
//    @OnClick({R.id.csl_1, R.id.csl_2, R.id.csl_3, R.id.csl_4, R.id.csl_5, R.id.csl_6})
//    public void onViewClicked(View view) {
//        Intent intent = new Intent(TestIntroduceActivity.this, TestActivity.class);
//        switch (view.getId()) {
//            case R.id.csl_1:
//                intent.putExtra(Constants.INTENT_ID, 0);
//                break;
//            case R.id.csl_2:
//                intent.putExtra(Constants.INTENT_ID, 1);
//                break;
//            case R.id.csl_3:
//                intent.putExtra(Constants.INTENT_ID, 2);
//                break;
//            case R.id.csl_4:
//                intent.putExtra(Constants.INTENT_ID, 3);
//                break;
//            case R.id.csl_5:
//                intent.putExtra(Constants.INTENT_ID, 4);
//                break;
//            case R.id.csl_6:
//                intent.putExtra(Constants.INTENT_ID, 5);
//                break;
//        }
//        startActivity(intent);
//    }
}
