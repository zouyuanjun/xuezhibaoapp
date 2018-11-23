package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiatingCourseAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.JiatingCourseBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.immodule.JGApplication;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.presenter.TeacherPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.helputils.CreatDiag;
import com.xinzhu.xuezhibao.view.interfaces.TeacherInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StatusBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherDetailActivity extends BaseActivity implements TeacherInterface {
    CommentAdapter commentAdapter;
    RvJiatingCourseAdapter adapter;
    WeakReference<Context> mContext;
    String teacherId;
    TeacherPresenter teacherPresenter;
    @BindView(R.id.detail_page_image)
    ImageView detailPageImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.im_like)
    ImageView imLike;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.im_talk)
    ImageView imTalk;
    @BindView(R.id.tv_commend)
    TextView tvCommend;
    @BindView(R.id.tab_techer)
    TabLayout tabTecher;
    @BindView(R.id.rv_teacher_course)
    RecyclerView rvTeacherCourse;
    @BindView(R.id.tv_intro)
    WebView wbFeedback;
    @BindView(R.id.teacherhead)
    SimpleDraweeView teacherhead;
    int coursepage = 1;
    int commentpage = 1;
    List<CourseBean> courseBeanList = new ArrayList<>();
    LinkedList<CommentBean> commentBeanList = new LinkedList<>();
    Activity activity;
    TeacherBean teacherBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setColor(this, Color.parseColor("#f87d28"));
        setContentView(R.layout.activity_teacher_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);//设置toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        teacherPresenter = new TeacherPresenter(this);
        teacherId = getIntent().getStringExtra(Constants.INTENT_ID);
        coursepage = 1;
        commentpage = 1;
        activity = this;
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);//设置展开后标题的颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
        tabTecher.addTab(tabTecher.newTab().setText("简介"));
        tabTecher.addTab(tabTecher.newTab().setText("课程"));
        tabTecher.addTab(tabTecher.newTab().setText("评价"));
        mContext = new WeakReference(this);

        commentAdapter = new CommentAdapter(this, commentBeanList);
        adapter = new RvJiatingCourseAdapter(mContext, courseBeanList);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeacherCourse.setLayoutManager(linearLayoutManager3);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabTecher.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    wbFeedback.setVisibility(View.VISIBLE);
                    rvTeacherCourse.setVisibility(View.GONE);
                } else if (position == 1) {
                    rvTeacherCourse.setAdapter(adapter);
                } else if (position == 2) {
                    rvTeacherCourse.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    wbFeedback.setVisibility(View.GONE);
                    rvTeacherCourse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter.setOnItemClickListener(new RvJiatingCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TeacherDetailActivity.this, CourseDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, courseBeanList.get(position).getCurriculumId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        WebSettings webSettings = wbFeedback.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        wbFeedback.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wbFeedback.setWebViewClient(new MyWebViewClient());
        wbFeedback.addJavascriptInterface(this, "App");
    }

    @Override
    protected void onResume() {
        super.onResume();
        teacherPresenter.getTeacherDetail(teacherId);
        teacherPresenter.getTeacherCourse(teacherId, coursepage);
        teacherPresenter.getComment(teacherId, commentpage);
    }


    @OnClick({R.id.im_like, R.id.im_talk, R.id.tv_commend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_like:
                break;
            case R.id.im_talk:
                Intent notificationIntent = new Intent(activity, ChatActivity.class);
                notificationIntent.putExtra(JGApplication.TARGET_ID, teacherBean.getMainPhone());
                notificationIntent.putExtra(JGApplication.CONV_TITLE, teacherBean.getRealName()+"老师");
                notificationIntent.putExtra(JGApplication.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
                startActivity(notificationIntent);//自定义跳转到指定页面
                break;
            case R.id.tv_commend:
                showpop(view);
                break;
        }
    }

    @Override
    public void getTeacherDetail(TeacherBean teacherBean) {
        if (null != teacherBean) {
            collapsingToolbar.setTitle(teacherBean.getRealName() + "老师");//设置标题的名字
            teacherhead.setImageURI(teacherBean.getHeadPortraitUrl());
            wbFeedback.loadDataWithBaseURL(null, teacherBean.getDescribeInfo(), "text/html", "UTF-8", null);
            this.teacherBean=teacherBean;
        }
    }

    @Override
    public void getTeacherCourse(List<CourseBean> list) {
        if (null != list) {
            courseBeanList.addAll(list);
            adapter.notifyDataSetChanged();
            coursepage++;
        }
    }

    @Override
    public void getTeacherComment(List<CommentBean> list, String total) {
        if (null != list) {
            commentBeanList.addAll(list);
            adapter.notifyDataSetChanged();
            commentpage++;
        }
    }

    @Override
    public void nomoredata() {

    }

    @Override
    public void err(int code) {

    }

    //弹出发送评论对话框
    private void showpop(View view) {
        if (Constants.TOKEN.isEmpty()) {
            CreatDiag.shoudia(activity);
        } else {
            View view1 = LayoutInflater.from(activity).inflate(R.layout.pop_commend, null);
            final EditText editText = view1.findViewById(R.id.ed_comment);
            TextView textView = view1.findViewById(R.id.tv_send);
            final PopupWindow window = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            window.setOutsideTouchable(true);
            window.setTouchable(true);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String commend = editText.getText().toString();
                    CommentBean commentBean = new CommentBean(Constants.userBasicInfo.getImage(), Constants.userBasicInfo.getNickName(), System.currentTimeMillis(), commend, "", "111");
                    commentBeanList.addFirst(commentBean);
                    commentAdapter.notifyItemInserted(0);
                    teacherPresenter.sendComment(teacherId, commend);
                    window.dismiss();
                }
            });
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            imgReset();
            wbFeedback.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
            super.onPageFinished(view, url);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        private void imgReset() {
            wbFeedback.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    " img.style.maxWidth = '100%';img.style.height='auto';" +
                    "}" +
                    "})()");
        }
    }

    @JavascriptInterface
    public void resize(final float height) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) wbFeedback.getLayoutParams();
                layoutParams.width = (int) ((getResources().getDisplayMetrics().widthPixels));
                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density) + 50;
                Log.d(layoutParams.width + "高度是" + layoutParams.height + "原始" + height + "级" + getResources().getDisplayMetrics().density);
                wbFeedback.setLayoutParams(layoutParams);
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                //    wbFeedback.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

}
