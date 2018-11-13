package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvXuekeCourseAdapter;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.SelectConditionBean;
import com.xinzhu.xuezhibao.presenter.CoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.CourseDetailActivity;
import com.xinzhu.xuezhibao.view.interfaces.SubjectCourseInterface;
import com.zou.fastlibrary.ui.spinner.NiceSpinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 学宝页面全部课程里的学科页面
 */
public class SubjectCourseFragment extends LazyLoadFragment implements SubjectCourseInterface {

    Unbinder unbinder;
    RvXuekeCourseAdapter adapter;
    WeakReference<Context> mContext;
    @BindView(R.id.sp_zonghe)
    NiceSpinner spZonghe;
    @BindView(R.id.sp_nianji)
    NiceSpinner spNianji;
    @BindView(R.id.sp_kemu)
    NiceSpinner spKemu;
    @BindView(R.id.rv_xueke)
    RecyclerView rvXueke;
    CoursePresenter coursePresenter;
    List<SelectConditionBean> myclasstypelist = new ArrayList<>();
    List<SelectConditionBean> mycoursetypelist = new ArrayList<>();
    List<CourseBean> xuekeCourseBeanList = new ArrayList<>();
    String subjectDictionaryId;
    String classDictionaryId;
    int TYPE;
    int page = 1;
    int oldclassselect = -1;
    int oldsubject = -1;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.im_blank)
    ImageView imBlank;

    @Override
    protected int setContentView() {
        return R.layout.fragment_xueke;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getInt("TYPE");
        }
    }

    @Override
    protected void lazyLoad() {
        coursePresenter = new CoursePresenter(this);
        coursePresenter.getseleectcondition();
        mContext = new WeakReference(MyApplication.getContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (TYPE == 1) {
                    coursePresenter.getSubjectHotCourse(page, "", "");
                } else if (TYPE == 2) {
                    coursePresenter.getSubjectNewCourse(page, "", "");
                } else if (TYPE == 3) {
                    coursePresenter.getSubjectRecommendCourse(page, "", "");
                } else if (TYPE == 4) {
                    coursePresenter.getSubjectCourse(page, "", "");
                }
            }
        });

        rvXueke.setLayoutManager(linearLayoutManager3);
        spKemu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == oldsubject) {
                    return;
                }
                oldsubject = i;
                page = 1;
                xuekeCourseBeanList.clear();
                adapter.notifyDataSetChanged();
                if (i == 0) {
                    subjectDictionaryId=null;
                    if (TYPE == 1) {
                        coursePresenter.getSubjectHotCourse(page, "", classDictionaryId);
                    } else if (TYPE == 2) {
                        coursePresenter.getSubjectNewCourse(page, "", classDictionaryId);
                    } else if (TYPE == 3) {
                        coursePresenter.getSubjectRecommendCourse(page, "", classDictionaryId);
                    } else if (TYPE == 4) {
                        coursePresenter.getSubjectCourse(page, "", classDictionaryId);
                    }
                    return;
                }

                if (i > 0) {
                    subjectDictionaryId = mycoursetypelist.get(i - 1).getDictionaryId() + "";
                }
                if (TYPE == 1) {
                    coursePresenter.getSubjectHotCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 2) {
                    coursePresenter.getSubjectNewCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 3) {
                    coursePresenter.getSubjectRecommendCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 4) {
                    coursePresenter.getSubjectCourse(page, subjectDictionaryId, classDictionaryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spNianji.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    classDictionaryId = myclasstypelist.get(i - 1).getDictionaryId() + "";
                }
                if (i == oldclassselect) {
                    return;
                }
                oldclassselect = i;
                page = 1;
                xuekeCourseBeanList.clear();
                adapter.notifyDataSetChanged();
                if (i == 0) {
                    classDictionaryId=null;
                    if (TYPE == 1) {
                        coursePresenter.getSubjectHotCourse(page, subjectDictionaryId, "");
                    } else if (TYPE == 2) {
                        coursePresenter.getSubjectNewCourse(page, subjectDictionaryId, "");
                    } else if (TYPE == 3) {
                        coursePresenter.getSubjectRecommendCourse(page, subjectDictionaryId, "");
                    } else if (TYPE == 4) {
                        coursePresenter.getSubjectCourse(page, subjectDictionaryId, "");
                    }
                    return;
                }
                if (TYPE == 1) {
                    coursePresenter.getSubjectHotCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 2) {
                    coursePresenter.getSubjectNewCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 3) {
                    coursePresenter.getSubjectRecommendCourse(page, subjectDictionaryId, classDictionaryId);
                } else if (TYPE == 4) {
                    coursePresenter.getSubjectCourse(page, subjectDictionaryId, classDictionaryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new RvXuekeCourseAdapter(mContext, xuekeCourseBeanList);
        rvXueke.setAdapter(adapter);
        if (TYPE == 1) {
            coursePresenter.getSubjectHotCourse(page, "", "");
        } else if (TYPE == 2) {
            coursePresenter.getSubjectNewCourse(page, "", "");
        } else if (TYPE == 3) {
            coursePresenter.getSubjectRecommendCourse(page, "", "");
        } else if (TYPE == 4) {
            coursePresenter.getSubjectCourse(page, "", "");
        }

        adapter.setOnItemClickListener(new RvXuekeCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, xuekeCourseBeanList.get(position).getCurriculumId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvXueke.setAdapter(null);
        page = 1;
        unbinder.unbind();
    }


    @Override
    public void getSubjectCourse(List<CourseBean> list) {
        xuekeCourseBeanList.addAll(list);
        adapter.notifyDataSetChanged();
        page++;
        refreshLayout.finishLoadMore();
        imBlank.setVisibility(View.GONE);
    }

    @Override
    public void getSubjectHotCourse(List<CourseBean> list) {

    }

    @Override
    public void getSeachCondition(List<SelectConditionBean> classtypelist, List<SelectConditionBean> coursetypelist) {
        LinkedList<String> data = new LinkedList<>(Arrays.asList("综合排序"));
        spZonghe.attachDataSource(data);
        LinkedList<String> classtype = new LinkedList<>();
        classtype.addFirst("全部");
        for (SelectConditionBean selectConditionBean : classtypelist) {
            classtype.add(selectConditionBean.getDictionaryName());
            myclasstypelist.add(selectConditionBean);
        }
        spNianji.attachDataSource(classtype);
        LinkedList<String> coursetype = new LinkedList<>();
        coursetype.addFirst("全部");
        for (SelectConditionBean selectConditionBean : coursetypelist) {
            coursetype.add(selectConditionBean.getDictionaryName());
            mycoursetypelist.add(selectConditionBean);
        }
        spKemu.attachDataSource(coursetype);

    }

    @Override
    public void noMoreData() {
        refreshLayout.finishLoadMoreWithNoMoreData();
        if (xuekeCourseBeanList.size() == 0) {
            imBlank.setVisibility(View.VISIBLE);
        }
    }
}
