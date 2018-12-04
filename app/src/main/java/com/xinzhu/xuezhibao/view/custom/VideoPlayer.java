package com.xinzhu.xuezhibao.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zou.fastlibrary.utils.Log;

public class VideoPlayer extends StandardGSYVideoPlayer {
    boolean canpaly = false;
    ImageView imageButton;

    public VideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void clickStartIcon() {
        if (canpaly) {
            Log.d("可以播放了");
            super.clickStartIcon();
        } else {
            Log.d("还不能播放");
        }

    }

    public boolean isCanpaly() {
        return canpaly;
    }

    public void setCanpaly(boolean canpaly) {
        this.canpaly = canpaly;
    }

    public void imbsetListener(OnClickListener onClickListener) {
        imageButton = getBackButton();
        imageButton.setOnClickListener(onClickListener);

    }

    public void startbuListener(OnClickListener onClickListener) {
        getStartButton().setOnClickListener(onClickListener);
    }

    public void hidstartbt() {
        setViewShowState(mStartButton, INVISIBLE);
    }
    public void showstartbt() {
        setViewShowState(mStartButton, VISIBLE);
    }

    public boolean isPlay() {
        return mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_PREPAREING || mCurrentState == CURRENT_STATE_PLAYING;
    }
}
