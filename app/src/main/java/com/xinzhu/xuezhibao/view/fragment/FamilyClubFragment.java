package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.PotionsGoodsAdapter2;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.GoodsComment;
import com.xinzhu.xuezhibao.presenter.MyGoodsPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.DialogUtils;
import com.xinzhu.xuezhibao.view.activity.GoodsDetailActivity;
import com.xinzhu.xuezhibao.view.activity.MyPointsActivity;
import com.xinzhu.xuezhibao.view.activity.MyVipCentreActivity;
import com.xinzhu.xuezhibao.view.activity.PointsMallTabActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyGoodsInterface;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FamilyClubFragment extends LazyLoadFragment implements MyGoodsInterface {

    Unbinder unbinder;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.sdv_photo)
    SimpleDraweeView sdvPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.textView35)
    TextView textView35;
    @BindView(R.id.tv_togovipcenter)
    TextView tvTogovipcenter;
    @BindView(R.id.tv_pointsnum)
    TextView tvPointsnum;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_more_goods)
    TextView tvMoreGoods;
    @BindView(R.id.rv_shop)
    RecyclerView rvShop;
    PotionsGoodsAdapter2 potionsGoodsAdapter;
    MyGoodsPresenter myoGoodsPresenter;
    public List<GoodsBean> goodsBeanList = new ArrayList<>();
    @BindView(R.id.scb_viplv)
    ShapeCornerBgView scbViplv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myoGoodsPresenter = new MyGoodsPresenter(this);

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_framilyclub;
    }

    @Override
    protected void lazyLoad() {
//        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
//        rvShop.setLayoutManager(layoutManage);
//        potionsGoodsAdapter = new PotionsGoodsAdapter2(getContext(), goodsBeanList);
//        potionsGoodsAdapter.setOnItemClickListener(new PotionsGoodsAdapter2.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
//                intent.putExtra(Constants.INTENT_ID, goodsBeanList.get(position).getProductId());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });
//        rvShop.setAdapter(potionsGoodsAdapter);
//        goodsBeanList.clear();
//        myoGoodsPresenter.getGoodsList(1, 1, 100000);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != Constants.userBasicInfo) {
            sdvPhoto.setImageURI(Constants.userBasicInfo.getImage());
            tvName.setText(Constants.userBasicInfo.getNickName());
            scbViplv.setText(Constants.userBasicInfo.getDictionaryName());
            tvPointsnum.setText(Constants.userBasicInfo.getIntegral() + "");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        rvShop.setLayoutManager(layoutManage);
        potionsGoodsAdapter = new PotionsGoodsAdapter2(getContext(), goodsBeanList);
        potionsGoodsAdapter.setOnItemClickListener(new PotionsGoodsAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (StringUtil.isEmpty(Constants.TOKEN)){
                    DialogUtils.loginDia(getActivity());
                    return;
                }
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID, goodsBeanList.get(position).getProductId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rvShop.setAdapter(potionsGoodsAdapter);
        goodsBeanList.clear();
        myoGoodsPresenter.getGoodsList(1, 1, 100000);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.tv_togovipcenter, R.id.tv_points, R.id.tv_more_goods})
    public void onViewClicked(View view) {
        if (StringUtil.isEmpty(Constants.TOKEN)) {
            DialogUtils.loginDia(getActivity());
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_togovipcenter:
                intent = new Intent(getActivity(), MyVipCentreActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_points:
                intent = new Intent(getActivity(), MyPointsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_more_goods:
                intent = new Intent(getActivity(), PointsMallTabActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getGoodsList(List<GoodsBean> list) {
        int b = list.size();
        if (b > 3) {
            b = 3;   //最多只展示4个商品
        }
        if (null != potionsGoodsAdapter) {
            goodsBeanList.clear();
            for (int i = 0; i < b; i++) {
                goodsBeanList.add(list.get(i));
            }
            potionsGoodsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void noMoreData() {

    }

    @Override
    public void getGoodsDetail(GoodsBean goodsBean) {

    }

    @Override
    public void getgrade(float grade) {

    }

    @Override
    public void getcomment(List<GoodsComment> list) {

    }
}
