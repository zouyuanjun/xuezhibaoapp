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
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

/**
 * 主页中视频列表adapter
 */
public class HomeVideoAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<VideoVoiceBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeVideoAdapter(Context mContext, List<VideoVoiceBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_video, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((RecyclerHolder) holder).title.setText(mDatas.get(position).getVideoTitle());
        ((RecyclerHolder) holder).readnum.setText(mDatas.get(position).getVideoLook());
        ((RecyclerHolder) holder).im_video.setImageURI(mDatas.get(position).getVideoPicture());


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

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView title;
        SimpleDraweeView im_video;
        TextView readnum;
        private RecyclerHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_video_titletv);
            readnum=itemView.findViewById(R.id.tv_playnum);
            im_video = itemView.findViewById(R.id.im_video);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


}