package com.essam.youtubeplayer.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.essam.youtubeplayer.R;
import com.essam.youtubeplayer.utils.Consts;
import com.essam.youtubeplayer.model.Item;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity {
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private TextView mTitleTv, mDescriptionTv, mViewsCountTv, mLikesCountTv, mDislikesCountTv;
    private String videoId;
    private Item mItem;

    private static final String TAG = VideoPlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mTitleTv = findViewById(R.id.tv_title);
        mDescriptionTv = findViewById(R.id.tv_description);
        mViewsCountTv = findViewById(R.id.tv_views_count);
        mLikesCountTv = findViewById(R.id.tv_likes_count);
        mDislikesCountTv = findViewById(R.id.tv_dislikes_count);

        initYoutubePlayer();
        receiveIntent();
    }

    /**
     * Receive Video Item that contains details about video such id, title, description
     */
    private void receiveIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Consts.VIDEO_ITEM)){
            mItem =(Item) intent.getSerializableExtra(Consts.VIDEO_ITEM);
            if (mItem != null) {
                videoId = mItem.getId();
                //initialize YoutubePlayer
                mYouTubePlayerView.initialize(Consts.GOOGLE_API_KEY,mOnInitializedListener);
                // populate video info to Ui
                populateVideoData();
            }
        }
    }

    /**
     * This method handles YoutubePlayerView Simply it plays the video with the id passed
     * for more details and customizations check out YouTube Android Player API :
     * https://developers.google.com/youtube/android/player
     */
    private void initYoutubePlayer(){
        mYouTubePlayerView = findViewById(R.id.youtubePlayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.i(TAG, "onInitializationSuccess: START LOADING VIDEO ...");
                // if orientation is landscape > enable full screen
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    youTubePlayer.setFullscreen(true);
                }
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i(TAG, "onInitializationFailure: FAILED TO INITIALIZE YOUTUBE PLAYER ...");

            }
        };
    }

    private void populateVideoData() {
        mTitleTv.setText(mItem.getVideo().getTitle());
        mDescriptionTv.setText(mItem.getVideo().getDescription());
        mViewsCountTv.setText(mItem.getStatistic().getViewCount());
        mLikesCountTv.setText(mItem.getStatistic().getLikeCount());
        mDislikesCountTv.setText(mItem.getStatistic().getDislikeCount());
    }
}