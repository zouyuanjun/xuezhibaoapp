package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.GoodsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 由于家长会展示的商品图片更小，所以再写一个adapter
 */
public class PotionsGoodsAdapter2 extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<GoodsBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PotionsGoodsAdapter2(Context mContext, List<GoodsBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shoping2, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((RecyclerHolder) holder).tvTitle.setText(mDatas.get(position).getProductName());
        ((RecyclerHolder) holder).tvPaynum.setText( mDatas.get(position).getBuyNum()+"人已购买");
        ((RecyclerHolder) holder).tvPrice.setText(mDatas.get(position).getProductPrice()+"积分");
        ((RecyclerHolder) holder).sdvPicter.setImageURI(mDatas.get(position).getProductImg());


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


    static  class RecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_picter)
        SimpleDraweeView sdvPicter;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_paynum)
        TextView tvPaynum;

        RecyclerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}