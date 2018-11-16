package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.AddressBean;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<AddressBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAddressAdapter(Context mContext, List<AddressBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myaddress, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvAddresdetail.setText(mDatas.get(position).getProvince()+mDatas.get(position).getCity()+mDatas.get(position).getCounty()+mDatas.get(position).getAddress());
        ((MyViewHolder) holder).tvName.setText(mDatas.get(position).getLinkman());
        ((MyViewHolder) holder).tvPhone.setText(mDatas.get(position).getLinkPhone());
        if (mDatas.get(position).getIsDefault()>0){
            ((MyViewHolder) holder).cbAddress.setChecked(true);
        }else {
            ((MyViewHolder) holder).cbAddress.setChecked(false);
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            ((MyViewHolder) holder).tvEditaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onEditClick(v, position);
                }
            });
            ((MyViewHolder) holder).tvDetailaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onDetalClick(v, position);
                }
            });
            ((MyViewHolder) holder).cbAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onCheckboxClick(v, position);
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
void onDetalClick(View view, int position);
        void onEditClick(View view, int position);
        void onCheckboxClick(View view, int position);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_addresdetail)
        TextView tvAddresdetail;
        @BindView(R.id.cb_address)
        CheckBox cbAddress;
        @BindView(R.id.tv_editaddress)
        TextView tvEditaddress;
        @BindView(R.id.tv_detailaddress)
        TextView tvDetailaddress;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}