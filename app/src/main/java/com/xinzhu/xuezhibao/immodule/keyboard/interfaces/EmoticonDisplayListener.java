package com.xinzhu.xuezhibao.immodule.keyboard.interfaces;

import android.view.ViewGroup;

import com.xinzhu.xuezhibao.immodule.keyboard.adpater.EmoticonsAdapter;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
