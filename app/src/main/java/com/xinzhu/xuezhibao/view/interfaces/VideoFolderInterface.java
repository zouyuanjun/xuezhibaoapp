package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.VideoFolder;

import java.util.List;

public interface VideoFolderInterface extends publiceviewinterface {
    void getvideofolder(List<VideoFolder> videoFolderlist);
    void nomoredata();
}
