package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;

import java.util.List;

public interface LikeCollectInterface extends publiceviewinterface{
//    void getcomment(List<CommentBean> mDatas, int total);
//    void getcommentfail();
    void islike(boolean islike);
    void iscollect(boolean iscollect);
}
