package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointsRuleActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.textView22)
    TextView textView22;
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String result= (String) msg.obj;
        int what=msg.what;
        String data=JsonUtils.getStringValue(result,"Data");
        String describeContent=JsonUtils.getStringValue(data,"describeContent");
        if (!StringUtil.isEmpty(describeContent)){
            textView22.setText(Html.fromHtml(describeContent));
        }else {
            textView22.setText("获取数据失败，请稍后再试");
        }

    }
};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointsrule);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String data= JsonUtils.keyValueToString("describeType",1);
        Network.getnetwork().postJson(data, Constants.URL+"/guest/integral-rules",handler,1);
    }
}
