package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyorderAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<OrderBean> mDatas;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public MyorderAdapter(Context mContext, List<OrderBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvTitle.setText(mDatas.get(position).getName());
        ((MyViewHolder) holder).tvOther.setText("—"+mDatas.get(position).getOrderPrice()+"积分");
        ((MyViewHolder) holder).tvPrice.setText("￥"+mDatas.get(position).getPrice());
        ((MyViewHolder) holder).tvOrdertype.setText(mDatas.get(position).getDictionaryName());
        if (null!=mDatas.get(position).getDictionaryName()&&mDatas.get(position).getDictionaryName().equals("学科课程") && mDatas.get(position).getState().equals("100")) {
            ((MyViewHolder) holder).cslAction.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).tvActionone.setText("申请退款");
            ((MyViewHolder) holder).tvActionone.setBackground(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.dialogbg_nor));
        }
        if (mDatas.get(position).getState().equals("2")){
            ((MyViewHolder) holder).tvOrderstuts.setText("等待商城发货");
            ((MyViewHolder) holder).tvOrdertype.setText("积分商城");
            ((MyViewHolder) holder).tvOther.setText("—"+mDatas.get(position).getOrderPrice()+"积分");
        }
        if (mDatas.get(position).getState().equals("3")){
            ((MyViewHolder) holder).cslAction.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).tvActionone.setText("确认收货");
            ((MyViewHolder) holder).tvOrdertype.setText("积分商城");
            ((MyViewHolder) holder).tvActionone.setBackground(ContextCompat.getDrawable(MyApplication.getContext(),R.drawable.feedbacktextbg));
        }
        if (mDatas.get(position).getState().equals("4")){
            ((MyViewHolder) holder).cslAction.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).tvOrderstuts.setText("待评价");
            ((MyViewHolder) holder).tvActionone.setText("发表评价");
            ((MyViewHolder) holder).tvOrdertype.setText("积分商城");
            ((MyViewHolder) holder).tvOther.setText("—"+mDatas.get(position).getOrderPrice()+"积分");
        }
        ((MyViewHolder) holder).imageView19.setImageURI(mDatas.get(position).getPicture());
        Log.d("加载一条数据");
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            ((MyViewHolder) holder).tvActionone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onactionOneClick(v, position);
                }
            });
            ((MyViewHolder) holder).tvActiontwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onactionTwoClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onactionOneClick(View view, int position);
        void onactionTwoClick(View view, int position);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ordertype)
        TextView tvOrdertype;
        @BindView(R.id.tv_orderstuts)
        TextView tvOrderstuts;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_mintitel)
        TextView tvMintitel;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_other)
        TextView tvOther;
        @BindView(R.id.imageView19)
        SimpleDraweeView imageView19;
        @BindView(R.id.tv_actionone)
        TextView tvActionone;
        @BindView(R.id.tv_actiontwo)
        TextView tvActiontwo;
        @BindView(R.id.csl_action)
        ConstraintLayout cslAction;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}