package com.xinzhu.xuezhibao.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zou.fastlibrary.utils.Log;

public class VideoPlayer extends StandardGSYVideoPlayer {
    boolean canpaly=false;
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
    protected void clickStartIcon() {
        if (canpaly){
            super.clickStartIcon();
        }else {
            Log.d("还不能播放");
        }

    }

    public boolean isCanpaly() {
        return canpaly;
    }

    public void setCanpaly(boolean canpaly) {
        this.canpaly = canpaly;
    }

    public void imbsetListener(OnClickListener onClickListener){
        imageButton=getBackButton();
        imageButton.setOnClickListener(onClickListener) ;

    }

   public void hidstartbt(){
       setViewShowState(mStartButton, INVISIBLE);
   }
}
