package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.VideoFolder;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.HomeVideoVoiceListInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoFolderInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoFragmentInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;


public class VideoVoiceListPresenter {
    HomeVideoVoiceListInterface homeVideoVoiceListInterface;
    VideoFragmentInterface videoFragmentInterface;
    VideoFolderInterface videoFolderInterface;

    public VideoVoiceListPresenter(VideoFolderInterface videoFolderInterface) {
        this.videoFolderInterface = videoFolderInterface;
    }

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
                code = JsonUtils.getIntValue(result, "Code");
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
            if (code == 100) {
                if (what == 9) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    List<VideoFolder> mDatas = JSON.parseArray(data, VideoFolder.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        videoFolderInterface.getvideofolder(mDatas);
                    } else {
                        videoFolderInterface.nomoredata();
                    }
                } else {
                    String data = JsonUtils.getStringValue(result, "Data");
                    List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        if (what == 8) {
                            videoFragmentInterface.getpayVideo(mDatas);
                        } else {
                            homeVideoVoiceListInterface.getVideo(mDatas);
                        }
                    } else {
                        if (null != videoFragmentInterface) {
                            videoFragmentInterface.noData();
                        } else if (null != homeVideoVoiceListInterface) {
                            homeVideoVoiceListInterface.nodata();
                        }
                    }
                }
            } else {
                if (null != videoFolderInterface) {
                    videoFolderInterface.nomoredata();
                } else if (null != videoFragmentInterface) {
                    videoFragmentInterface.noData();
                } else if (null != homeVideoVoiceListInterface) {
                    homeVideoVoiceListInterface.nodata();
                }
            }

        }
    };

    public void getHotVideo(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "type", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/videos-hottest", handler, 1);
    }

    public void getNewVideo(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "type", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest", handler, 2);
    }

    public void getLikeVideo(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)) {
            String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
            data = JsonUtils.addKeyValue(data, "type", 2);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-videos", handler, 3);
        }
    }

    public void getHotVoice(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "type", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/videos-hottest", handler, 4);
    }

    public void getNewVoice(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "type", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/video-newest", handler, 5);
    }

    public void getLikeVoice(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)) {
            String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
            data = JsonUtils.addKeyValue(data, "type", 1);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-videos", handler, 6);
        }
    }

    //根据文件夹ID查找整套视频
    public void getpayVideo(String videoid) {
        String data = JsonUtils.keyValueToString2("videoTypeId", videoid, "pageNo", 1);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "guest/select-video-type", handler, 8);
    }

    //获取视频文件夹
    public void getVideoFolder(int page, int videoTypeType) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "videoType", 1);
        data = JsonUtils.addKeyValue(data, "videoTypeType", videoTypeType);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "guest/select-video-types", handler, 9);
    }

    public void cancelmessage() {
        handler.removeCallbacksAndMessages(null);
    }
}
