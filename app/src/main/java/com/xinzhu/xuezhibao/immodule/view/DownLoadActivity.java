package com.xinzhu.xuezhibao.immodule.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.model.Message;


/**
 * Created by ${chenyn} on 2017/5/19.
 */

public class DownLoadActivity extends Activity {

    private Message mMessage;
    private TextView mFileName;
    private TextView mProcess;
    private ProgressBar mProcessBar;
    private Button mBtnDown;
    private FileContent mFileContent;
    private TextView mTv_titleName;
    private ImageView mIv_back;
    private int mProcessNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        mFileName = findViewById(R.id.file_name);
        mProcess = findViewById(R.id.process_num);
        mProcessBar = findViewById(R.id.processbar);
        mBtnDown = findViewById(R.id.btn_down);
        mTv_titleName = findViewById(R.id.tv_titleName);
        mIv_back = findViewById(R.id.iv_back);
        EventBus.getDefault().register(this);

        mFileName.setText(mFileContent.getFileName());
        mTv_titleName.setText(mFileContent.getFileName());
        long fileSize = mFileContent.getFileSize();
        mBtnDown.setText("下载(" + byteToMB(fileSize) + ")");
        mBtnDown.setTextColor(Color.WHITE);


        mBtnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBtnDown.setVisibility(View.GONE);
                mProcess.setVisibility(View.VISIBLE);
                mProcessBar.setVisibility(View.VISIBLE);
                mMessage.setOnContentDownloadProgressCallback(new ProgressUpdateCallback() {

                    @Override
                    public void onProgressUpdate(double percent) {
                        mProcessNum = (int) (percent * 100);
                        mProcess.setText(mProcessNum + "%");
                        mHandler.post(progressBar);
                    }
                });

                mFileContent.downloadFile(mMessage, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int responseCode, String responseMessage, File file) {
                        if (responseCode == 0) {
                            SharePreferenceManager.setIsOpen(true);
                            finish();
                        }else {
                            finish();
                        }
                    }
                });
            }
        });

        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            mProcessBar.setProgress(msg.arg1);
            mHandler.post(progressBar);
        }
    };

    Runnable progressBar = new Runnable() {
        @Override
        public void run() {
            android.os.Message msg = mHandler.obtainMessage();
            msg.arg1 = mProcessNum;

            mHandler.sendMessage(msg);
            if (mProcessNum == 100) {
                mHandler.removeCallbacks(progressBar);
            }
        }
    };

    private String byteToMB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size > kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void receiveUser(Message message) {
        mMessage = message;
        mFileContent = (FileContent) message.getContent();

    }
}
