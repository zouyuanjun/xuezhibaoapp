package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.ArticleInterface;
import com.xinzhu.xuezhibao.view.interfaces.ArticleListInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
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

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code=-999;
            try {
                code = JsonUtils.getIntValue(result, "_code");
            }catch (Exception e){
                com.zou.fastlibrary.utils.Log.d("异常了");
                articleListInterface.servererr();
            }
            if (code == -100) {
                articleListInterface.networkerr();
                return;
            }
            if (code == -200) {
                articleListInterface.networktimeout();
                return;
            }
            if (what==1){
                String data=JsonUtils.getStringValue(result,"Data");
                List<ArticleBean> mDatas=JSON.parseArray(data,ArticleBean.class);
                Log.d(mDatas.toString()+mDatas.size());
                articleListInterface.getHotArticle(mDatas);
            }else if (what==2){
                String data=JsonUtils.getStringValue(result,"Data");
                List<ArticleBean> mDatas=JSON.parseArray(data,ArticleBean.class);
                Log.d(mDatas.toString()+mDatas.size());
                articleListInterface.getNewArticle(mDatas);
            }else if (what==3){
                String data=JsonUtils.getStringValue(result,"Data");
                ArticleBean articleBean= (ArticleBean) JsonUtils.stringToObject(data,ArticleBean.class);
                articleInterface.getarticledetils(articleBean);
            }else if (what==5){
                if (code==203){
                    articleInterface.getcommentfail();
                }else if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    String total=JsonUtils.getStringValue(data,"total");
                    data=JsonUtils.getStringValue(data,"rows");
                    List<CommentBean> mDatas=JSON.parseArray(data,CommentBean.class);
                    articleInterface.getcomment(mDatas,total);
                }

            }
        }
    };
    public void getNewArticle(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/article-newest",handler,1);
    }
    public void getHotArticle(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/article-hottest",handler,2);
    }

    public void getArticleDetils(String id){
        String data=JsonUtils.keyValueToString("articleId",id);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/find-by-article-id",handler,3);
    }
    public void sendComment(String id,String comment){
        SendCommentBean sendCommentBean=new SendCommentBean(Constants.TOKEN,comment,id,"1");
        String data=JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data,Constants.URL+"/app/comment-add-article",handler,4);
    }

    public void getComment(String id,int page){
        String data=JsonUtils.keyValueToString2("pageNo",page,"productId",id);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/comment-article",handler,5);
    }
}
