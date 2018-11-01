package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hrb.library.MiniMusicView;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoiceDetilsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_readnum)
    TextView tvReadnum;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.mmv_music)
    MiniMusicView mMusicView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_details);
        ButterKnife.bind(this);
        mMusicView.setTitleText("孩子回家不吃饭");
        mMusicView.setmContext(new WeakReference<Context>(this));
        mMusicView.startPlayMusic("http://other.web.nf01.sycdn.kuwo.cn/resource/n2/87/0/3398589179.mp3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicView.stopPlayMusic();
    }
}
