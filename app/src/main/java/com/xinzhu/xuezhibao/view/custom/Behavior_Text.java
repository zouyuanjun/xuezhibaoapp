package com.xinzhu.xuezhibao.view.custom;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.zou.fastlibrary.utils.Log;

public class Behavior_Text extends CoordinatorLayout.Behavior {
    private float deltaY;
    public Behavior_Text() {
        super();
    }

    public Behavior_Text(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }
        Log.d("deltaY："+deltaY+"  dependency:"+dependency.getY()+"  child:"+child.getHeight());
        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        Log.d("  dy:"+dy);
        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(y);

        return true;
    }
}
