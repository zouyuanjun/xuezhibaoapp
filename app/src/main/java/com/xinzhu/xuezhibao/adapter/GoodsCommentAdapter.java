package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.GoodsComment;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsCommentAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<GoodsComment> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GoodsCommentAdapter(Context mContext, List<GoodsComment> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goodscomment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvCreattime.setText(TimeUtil.getWholeTime2(mDatas.get(position).getCreateTime()));
        ((MyViewHolder) holder).tvCommentDetils.setText(mDatas.get(position).getContent());
        ((MyViewHolder) holder).tv_userName.setText(mDatas.get(position).getNickname());
        ((MyViewHolder) holder).sdvPhoto.setImageURI(mDatas.get(position).getImage());
        if (!StringUtil.isEmpty(mDatas.get(position).getPicture())) {
            String[] s = mDatas.get(position).getPicture().split(",");
            if (s.length > 0) {
                ((MyViewHolder) holder).llImg.setVisibility(View.VISIBLE);
                for (int i = 0; i < s.length; i++) {
                    if (i == 0) {
                        Glide.with(mContext).load(s[i]).into(((MyViewHolder) holder).im1);
                    } else if (i == 1) {
                        Glide.with(mContext).load(s[i]).into(((MyViewHolder) holder).im2);
                    } else if (i == 2) {
                        Glide.with(mContext).load(s[i]).into(((MyViewHolder) holder).im3);
                    }

                }
            }
        }
        Log.d("加载一条数据");
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return false;
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

        void onItemLongClick(View view, int position);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_photo)
        SimpleDraweeView sdvPhoto;
        @BindView(R.id.tv_userName)
        TextView tv_userName;
        @BindView(R.id.tv_creattime)
        TextView tvCreattime;
        @BindView(R.id.tv_comment_detils)
        TextView tvCommentDetils;
        @BindView(R.id.im_1)
        ImageView im1;
        @BindView(R.id.im_2)
        ImageView im2;
        @BindView(R.id.im_3)
        ImageView im3;
        @BindView(R.id.ll_img)
        LinearLayout llImg;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}