package com.essam.youtubeplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.essam.youtubeplayer.R;
import com.essam.youtubeplayer.model.Item;
import com.essam.youtubeplayer.utils.ProjectUtils;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<Item>mVideoList;
    private Context mContext;
    private OnItemClickedListener mOnItemClickedListener;

    public VideoListAdapter(Context context,OnItemClickedListener onItemClickedListener) {
        mContext = context;
        mOnItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener{
        void onItemClicked(Item item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mVideoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mVideoList != null){
            return mVideoList.size();
        }
        else return 0;
    }

    public void setItems(List<Item>items){
        mVideoList = items;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageThumb;
        private TextView mTitleTv, mViewsCountTv, mLikesCountTv, mDislikesCountTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageThumb = itemView.findViewById(R.id.iv_thumb);
            mTitleTv = itemView.findViewById(R.id.tv_title);
            mViewsCountTv = itemView.findViewById(R.id.tv_views_count);
            mLikesCountTv = itemView.findViewById(R.id.tv_likes_count);
            mDislikesCountTv = itemView.findViewById(R.id.tv_dislikes_count);
            itemView.setOnClickListener(this);
        }

        void bind(Item item){
            mTitleTv.setText(item.getVideo().getTitle());
            mViewsCountTv.setText(ProjectUtils.representStatistic(Integer.parseInt(item.getStatistic().getViewCount())));
            mLikesCountTv.setText(ProjectUtils.representStatistic(Integer.parseInt(item.getStatistic().getLikeCount())));
            mDislikesCountTv.setText(ProjectUtils.representStatistic(Integer.parseInt(item.getStatistic().getDislikeCount())));
            Glide.with(mContext).load(item.getVideo().getThumbnail().getStandardThumbnail().getUrl()).into(mImageThumb);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickedListener.onItemClicked(mVideoList.get(getAdapterPosition()));
        }
    }
}
