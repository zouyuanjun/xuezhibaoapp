package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.HomeVideoVoiceListInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoFragmentInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;


public class VideoVoiceListPresenter {
    HomeVideoVoiceListInterface homeVideoVoiceListInterface;
    VideoFragmentInterface videoFragmentInterface;

    public VideoVoiceListPresenter(VideoFragmentInterface videoFragmentInterface) {
        this.videoFragmentInterface = videoFragmentInterface;
    }

    public VideoVoiceListPresenter(HomeVideoVoiceListInterface homeVideoVoiceListInterface) {
        this.homeVideoVoiceListInterface = homeVideoVoiceListInterface;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code = -999;
            try {
                code = JsonUtils.getIntValue(result, "_code");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null != videoFragmentInterface) {
                    videoFragmentInterface.servererr();
                } else if (null != homeVideoVoiceListInterface) {
                    homeVideoVoiceListInterface.servererr();
                }
                return;
            }
            if (code == -100) {
                if (null != videoFragmentInterface) {
                    videoFragmentInterface.networkerr();
                } else if (null != homeVideoVoiceListInterface) {
                    homeVideoVoiceListInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null != videoFragmentInterface) {
                    videoFragmentInterface.networktimeout();
                } else if (null != homeVideoVoiceListInterface) {
                    homeVideoVoiceListInterface.networktimeout();
                }
                return;
            }
            if (code != 100) {
                if (null != videoFragmentInterface) {
                    videoFragmentInterface.noData();
                } else if (null != homeVideoVoiceListInterface) {
                    homeVideoVoiceListInterface.nodata();
                }
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                if (what == 7) {

                    videoFragmentInterface.getFreeVideo(mDatas);
                } else if (what == 8) {
                    videoFragmentInterface.getpayVideo(mDatas);
                } else {
                    homeVideoVoiceListInterface.getVideo(mDatas);
                }
            }

        }
    };

    public void getHotVideo(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/videos-hottest", handler, 1);
    }

    public void getNewVideo(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest-gratis", handler, 2);
    }

    public void getLikeVideo(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-videos", handler, 3);
    }

    public void getHotVoice(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-hottest", handler, 4);
    }

    public void getNewVoice(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest", handler, 5);
    }

    public void getLikeVoice(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-video", handler, 6);
    }

    public void getfreeVideo(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest-gratis", handler, 7);

    }

    public void getpayVideo(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest-pay", handler, 8);

    }

}
