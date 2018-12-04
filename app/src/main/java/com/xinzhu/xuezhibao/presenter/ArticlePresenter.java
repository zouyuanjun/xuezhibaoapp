package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.ClickLikeBean;
import com.xinzhu.xuezhibao.bean.CollectBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.ArticleInterface;
import com.xinzhu.xuezhibao.view.interfaces.ArticleListInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class ArticlePresenter {

    ArticleListInterface articleListInterface;
    ArticleInterface articleInterface;

    public ArticlePresenter(ArticleInterface articleInterface) {
        this.articleInterface = articleInterface;
    }

    public ArticlePresenter(ArticleListInterface articleListInterface) {
        this.articleListInterface = articleListInterface;
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
                if (null != articleInterface) {
                    articleInterface.servererr();
                }
                if (null != articleListInterface) {
                    articleListInterface.servererr();
                }
            }
            if (code == -100) {
                if (null != articleInterface) {
                    articleInterface.networkerr();
                }
                if (null != articleListInterface) {
                    articleListInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null != articleInterface) {
                    articleInterface.networktimeout();
                }
                if (null != articleListInterface) {
                    articleListInterface.networktimeout();
                }
                return;
            }
            if (what == 1) {
                if (code == 203) {
                    articleListInterface.getDataFail();
                } else if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    List<ArticleBean> mDatas = JSON.parseArray(data, ArticleBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        articleListInterface.getDataSuccessful(mDatas);
                    }else {
                        articleListInterface.getDataFail();
                    }

                }
            } else if (what == 3) {
                String data = JsonUtils.getStringValue(result, "Data");
                ArticleBean articleBean = JsonUtils.stringToObject(data, ArticleBean.class);
                articleInterface.getarticledetils(articleBean);
            } else if (what == 5) {
                if (code == 203) {
                    articleInterface.getcommentfail();
                } else if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    int total = JsonUtils.getIntValue(data, "total");
                    data = JsonUtils.getStringValue(data, "rows");
                    List<CommentBean> mDatas = JSON.parseArray(data, CommentBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        articleInterface.getcomment(mDatas, total);
                    }else {
                        articleInterface.getcommentfail();
                    }
                }
            } else if (what == 7) {
                if (code==100){
                    articleInterface.islike(JsonUtils.getbooleValue(result,"Data"));
                }
            }else if (what == 9) {
                if (code==100){
                    articleInterface.iscollect(JsonUtils.getbooleValue(result,"Data"));
                }
            }
        }


    };

    public void getNewArticle(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/article-newest", handler, 1);
    }

    public void getHotArticle(int page) {
        String data = JsonUtils.keyValueToString("pageNo", page);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/article-hottest", handler, 1);
    }
    public void getCollectArticle(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page,"token",Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-article", handler, 1);
    }
    public void getArticleDetils(String id) {
        String data = JsonUtils.keyValueToString("articleId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-article-by-id", handler, 3);
    }

    public void sendComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "1");
        String data = JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-comment", handler, 4);
    }

    public void getComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        data=JsonUtils.addKeyValue(data,"productType",1);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-find-by-productid", handler, 5);
    }

    public void like(String objectId) {
        ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, "1");
        String data = JsonUtils.objectToString(clickLikeBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/add-like", handler, 6);
    }

    public void islike(String objectId) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, "1");
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/check-is-like", handler, 7);
        }
    }
    //收藏
    public void collect(String objectId) {
        CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, "1");
        String data = JsonUtils.objectToString(clickLikeBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-collect", handler, 8);
    }
    //是否收藏
    public void iscollect(String objectId) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, "1");
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/check-is-collect", handler, 9);
        }
    }
    //取消收餐
    public void cancelcollect(String objectId) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, "1");
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/cancel-collect", handler, 10);
        }
    }
    //取消点赞
    public void cancellike(String objectId) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, "1");
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/cancel-like", handler, 11);
        }
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
