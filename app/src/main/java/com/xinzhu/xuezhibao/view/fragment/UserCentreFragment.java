package com.xinzhu.xuezhibao.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.EditAllActivity;
import com.xinzhu.xuezhibao.view.activity.LoginActivity;
import com.xinzhu.xuezhibao.view.activity.MyAddressActivity;
import com.xinzhu.xuezhibao.view.activity.MyCollectActivity;
import com.xinzhu.xuezhibao.view.activity.MyCourseActivity;
import com.xinzhu.xuezhibao.view.activity.MyOrderActivity;
import com.xinzhu.xuezhibao.view.activity.MyPointsActivity;
import com.xinzhu.xuezhibao.view.activity.MyTaskActivity;
import com.xinzhu.xuezhibao.view.activity.MyVideoActivity;
import com.xinzhu.xuezhibao.view.activity.MyVipCentreActivity;
import com.xinzhu.xuezhibao.view.activity.PointsMallTabActivity;
import com.xinzhu.xuezhibao.view.activity.TrickActivity;
import com.xinzhu.xuezhibao.view.activity.UserBaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserCentreFragment extends LazyLoadFragment {
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    Unbinder unbinder;
    @BindView(R.id.tv_loginbutton)
    TextView tvLoginbutton;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_viplv)
    TextView tvViplv;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.tv_myjifen)
    TextView tvMyjifen;
    @BindView(R.id.imageView15)
    ImageView imageView15;
    @BindView(R.id.cl_jifen)
    ConstraintLayout clJifen;
    @BindView(R.id.im_mytask)
    ImageView imMytask;
    @BindView(R.id.im_mycourse)
    ImageView imMycourse;
    @BindView(R.id.im_mylike)
    ImageView imMylike;
    @BindView(R.id.tv_userbasic)
    TextView tvUserbasic;
    @BindView(R.id.tv_vipcentre)
    TextView tvVipcentre;
    @BindView(R.id.tv_myorder)
    TextView tvMyorder;
    @BindView(R.id.tv_jifenshop)
    TextView tvJifenshop;
    @BindView(R.id.tv_myaddress)
    TextView tvMyaddress;
    @BindView(R.id.tv_lognout)
    TextView tvLognout;

    @Override
    protected int setContentView() {
        return R.layout.fragment_usercentre;
    }

    @Override
    protected void lazyLoad() {

    }
    @Override
    public void onResume() {
        super.onResume();
        if (Constants.TOKEN.isEmpty()) {
            llUser.setVisibility(View.GONE);
            clJifen.setVisibility(View.GONE);
            tvLoginbutton.setVisibility(View.VISIBLE);
        } else {
            tvLoginbutton.setVisibility(View.GONE);
            llUser.setVisibility(View.VISIBLE);
            clJifen.setVisibility(View.VISIBLE);
            tvUsername.setText(Constants.userBasicInfo.getNickName());
            tvViplv.setText(Constants.userBasicInfo.getDictionaryName());
            sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());
            tvMyjifen.setText(Constants.userBasicInfo.getIntegral()+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sd_myphoto,R.id.tv_loginbutton, R.id.cl_jifen, R.id.im_mytask, R.id.im_mycourse,R.id.im_myvideo, R.id.im_mylike, R.id.tv_userbasic, R.id.tv_vipcentre, R.id.tv_mytrack, R.id.tv_myorder, R.id.tv_jifenshop, R.id.tv_myaddress, R.id.tv_lognout})
    public void onViewClicked(View view) {
        if (Constants.TOKEN.isEmpty() && view.getId() != R.id.tv_loginbutton) {
            CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
            builder.setTitle("提示");
            builder.setMessage("您尚未登陆，现在就去登陆");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra(Constants.FROMAPP, "fss");
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_loginbutton:
                Intent intent2 = new Intent(getContext(), LoginActivity.class);
                intent2.putExtra(Constants.FROMAPP, "fss");
                startActivity(intent2);
                break;
            case R.id.cl_jifen:
                intent = new Intent(getActivity(), MyPointsActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.im_mytask:
                Intent intent4 = new Intent(getContext(), MyTaskActivity.class);
                startActivity(intent4);
                break;
            case R.id.im_mycourse:
                intent = new Intent(getActivity(), MyCourseActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.im_myvideo:
                intent = new Intent(getActivity(), MyVideoActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.im_mylike:
                Intent intent6 = new Intent(getContext(), MyCollectActivity.class);
                startActivity(intent6);
                break;
            case R.id.tv_userbasic:
                if (null == Constants.userBasicInfo.getNickName()||Constants.userBasicInfo.getNickName().isEmpty()) {
                    intent = new Intent(getActivity(), EditAllActivity.class);
                } else {
                    intent = new Intent(getActivity(), UserBaseActivity.class);
                }
                getActivity().startActivity(intent);
                break;
            case R.id.tv_vipcentre:
                intent = new Intent(getActivity(), MyVipCentreActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_mytrack:
                Intent intent3 = new Intent(getActivity(), TrickActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.tv_myorder:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_jifenshop:
                intent = new Intent(getActivity(), PointsMallTabActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_myaddress:
                intent = new Intent(getActivity(), MyAddressActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_lognout:
                String data=JsonUtils.keyValueToString("token",Constants.TOKEN);
                Network.getnetwork().postJson(data,Constants.URL+"/app/login-out",new Handler(),1);
                Constants.TOKEN = "";
                SharedPreferences sharedPreferences=DataKeeper.getRootSharedPreferences(MyApplication.getContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove("PHONE");
                editor.remove("PASSWORD");
                Intent intent5 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent5);
                getActivity().finish();
                break;
            case R.id.sd_myphoto:
                if (null == Constants.userBasicInfo.getNickName()||Constants.userBasicInfo.getNickName().isEmpty()) {
                    intent = new Intent(getActivity(), EditAllActivity.class);
                } else {
                    intent = new Intent(getActivity(), UserBaseActivity.class);
                }
                getActivity().startActivity(intent);
                break;
        }
    }
}
