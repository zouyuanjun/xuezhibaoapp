package com.xinzhu.xuezhibao.view.fragment;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.LazyLoadFragment;
import com.zou.fastlibrary.utils.Log;

public class Fragment_2  extends LazyLoadFragment{
    @Override
    protected int setContentView() {
        return R.layout.fragment_2;
    }

    @Override
    protected void lazyLoad() {
        Log.d("第二个Fragment可见了");
    }
}
