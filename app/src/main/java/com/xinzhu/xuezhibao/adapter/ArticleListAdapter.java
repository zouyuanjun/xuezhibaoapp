package com.xinzhu.xuezhibao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.zou.fastlibrary.utils.TimeUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<ArticleBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArticleListAdapter(WeakReference<Context> mContext, List<ArticleBean> mDatas) {
        this.mContext = mContext.get();
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_articlelist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tvItemTitle.setText(mDatas.get(position).getArticleTitle());
        ((MyViewHolder) holder).tvDianzan.setText(mDatas.get(position).getArticleLikeFalse()+"");
        ((MyViewHolder) holder).tvItemTime.setText(TimeUtil.getWholeTime2(mDatas.get(position).getCreateTime()));
        ((MyViewHolder) holder).tv_readnum.setText(mDatas.get(position).getArticleReadFalse());
       ((MyViewHolder) holder).simpleDraweeView.setImageURI(mDatas.get(position).getArticlePicture());

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


     class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.im_dianzan)
        ImageView imDianzan;
        @BindView(R.id.tv_dianzan)
        TextView tvDianzan;
        @BindView(R.id.im_watch_ioc)
        ImageView imWatchIoc;
        @BindView(R.id.tv_readnum)
        TextView tv_readnum;
        @BindView(R.id.imageView10)
        ImageView imageView10;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}