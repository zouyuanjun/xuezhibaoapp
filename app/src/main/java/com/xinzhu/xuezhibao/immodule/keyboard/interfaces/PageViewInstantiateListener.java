package com.xinzhu.xuezhibao.immodule.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.xinzhu.xuezhibao.immodule.keyboard.data.PageEntity;


public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
