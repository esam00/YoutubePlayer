package com.essam.youtubeplayer.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.essam.youtubeplayer.R;
import com.essam.youtubeplayer.adapter.VideoListAdapter;
import com.essam.youtubeplayer.ui.VideoPlayerActivity;
import com.essam.youtubeplayer.utils.Consts;
import com.essam.youtubeplayer.model.Item;
import com.essam.youtubeplayer.model.VideoListResponse;
import com.essam.youtubeplayer.utils.ProjectUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements VideoListAdapter.OnItemClickedListener {
    private VideoListAdapter mVideoListAdapter;
    private List<Item> mVideoItemsList;
    private SwipeRefreshLayout mSwipe;
    private ImageView mLogoIv;
    private HomeViewModel mHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLogoIv = findViewById(R.id.logo_iv);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.mVideoListResponseMutableLiveData.observe(this, new Observer<VideoListResponse>() {
            @Override
            public void onChanged(VideoListResponse videoListResponse) {
                updateUi(videoListResponse);
            }
        });

        initSwipeRefresh();
        initRecyclerView();
        getTrendingVideos();
    }

    private void initSwipeRefresh() {
        mSwipe = findViewById(R.id.swipe_refresh);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrendingVideos();
            }
        });
        mSwipe.setRefreshing(true);
    }

    private void initRecyclerView() {
        mVideoItemsList = new ArrayList<>();
        RecyclerView videoRecyclerView = findViewById(R.id.video_rv);
        mVideoListAdapter = new VideoListAdapter(HomeActivity.this,HomeActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(linearLayoutManager);
        videoRecyclerView.setAdapter(mVideoListAdapter);
    }

    private void getTrendingVideos() {
        if (ProjectUtils.isNetworkConnected(this)) {// check for internet connection first
            mSwipe.setRefreshing(true);
            mHomeViewModel.getTrendingVideos();
        }
        else { // No Internet Connection
            mSwipe.setRefreshing(false);
            mLogoIv.setVisibility(View.VISIBLE);
            Toast.makeText(HomeActivity.this, R.string.network_request_failed, Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar
                    .make(mSwipe, R.string.no_internet, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTrendingVideos();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
            snackbar.setBackgroundTint(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }
    }

    private void updateUi(VideoListResponse response) {
        mSwipe.setRefreshing(false);
        if (response != null){
            mVideoItemsList = response.getItems();
            mVideoListAdapter.setItems(mVideoItemsList);
            mVideoListAdapter.notifyDataSetChanged();
            mLogoIv.setVisibility(View.INVISIBLE);
            Toast.makeText(HomeActivity.this, R.string.videos_updated, Toast.LENGTH_SHORT).show();
        }else {
            mLogoIv.setVisibility(View.VISIBLE);
            Toast.makeText(HomeActivity.this, R.string.network_request_failed, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClicked(Item item) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra(Consts.VIDEO_ITEM,item);
        startActivity(intent);
    }
}