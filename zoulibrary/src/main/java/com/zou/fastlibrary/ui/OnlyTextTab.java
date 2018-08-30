package com.zou.fastlibrary.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.zou.fastlibrary.R;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

/**
 * Created by mjj on 2017/9/26
 */
public class OnlyTextTab extends BaseTabItem {

    private final TextView mTitle;

    public OnlyTextTab(Context context, String title) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_only_text, this, true);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        mTitle.setTextColor(checked ? 0xFFFFFFFF : 0x56000000);
    }

    @Override
    public void setMessageNumber(int number) {}

    @Override
    public void setHasMessage(boolean hasMessage) {}

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }
}
