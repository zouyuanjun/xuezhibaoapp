package com.xinzhu.xuezhibao.immodule;

import android.content.Intent;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.immodule.view.SearchForAddFriendActivity;


/**
 * Created by ${chenyn} on 2017/4/9.
 */

public class MenuItemController implements View.OnClickListener {
    private ConversationListActivity mFragment;

    public MenuItemController(ConversationListActivity fragment) {
        this.mFragment = fragment;
    }

    //会话界面的加号
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.create_group_ll:
//                mFragment.dismissPopWindow();
//                intent = new Intent(mFragment, CreateGroupActivity.class);
//                mFragment.startActivity(intent);
                break;
            case R.id.add_friend_with_confirm_ll:    //添加好友
                mFragment.dismissPopWindow();
                intent = new Intent(mFragment, SearchForAddFriendActivity.class);
                intent.setFlags(1);
                mFragment.startActivity(intent);
                break;
            case R.id.send_message_ll:     //发送消息
                mFragment.dismissPopWindow();
                intent = new Intent(mFragment, SearchForAddFriendActivity.class);
                intent.setFlags(2);
                mFragment.startActivity(intent);
                break;
            //扫描二维码
            case R.id.ll_saoYiSao:
//                intent = new Intent(mFragment, CommonScanActivity.class);
//                intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
//                mFragment.getContext().startActivity(intent);
                break;
            default:
                break;
        }

    }
}
