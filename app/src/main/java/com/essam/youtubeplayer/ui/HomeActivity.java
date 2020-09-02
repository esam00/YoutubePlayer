package com.essam.youtubeplayer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.essam.youtubeplayer.Api.ApiClient;
import com.essam.youtubeplayer.Api.ApiService;
import com.essam.youtubeplayer.R;
import com.essam.youtubeplayer.adapter.VideoListAdapter;
import com.essam.youtubeplayer.utils.Consts;
import com.essam.youtubeplayer.model.Item;
import com.essam.youtubeplayer.model.VideoListResponse;
import com.essam.youtubeplayer.utils.ProjectUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements VideoListAdapter.OnItemClickedListener {
    private VideoListAdapter mVideoListAdapter;
    private List<Item> mVideoItemsList;
    private SwipeRefreshLayout mSwipe;
    private ImageView mLogoIv;

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLogoIv = findViewById(R.id.logo_iv);

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

    /**
     * This method simply creates a Retrofit instance and make a network call to Youtube Data Api
     * and returns a list of videos that match the API request parameters.
     * For more details checkout the documentation : https://developers.google.com/youtube/v3/docs/videos/list
     */
    private void getTrendingVideos() {
        if (ProjectUtils.isNetworkConnected(this)) { // check for internet connection first
            Log.i(TAG, "Api request >>>>>>> getVideos" );
            Retrofit retrofit = ApiClient.getClient();
            ApiService client = retrofit.create(ApiService.class);
            Call<VideoListResponse> call = client.getVideos(
                    Consts.PART_QUERY_VALUE,
                    Consts.CHART_QUERY_VALUE,
                    Consts.REGION_CODE_QUERY_VALUE,
                    Consts.MAX_RESULT_QUERY_VALUE,
                    Consts.GOOGLE_API_KEY);
            call.enqueue(new Callback<VideoListResponse>() {
                @Override
                public void onResponse(@NonNull Call<VideoListResponse> call, @NonNull Response<VideoListResponse> response) {
                    mSwipe.setRefreshing(false);
                    if (response.isSuccessful() && response.body() != null) {
                        Log.i(TAG, "Get videos Response : " + response);
                        mVideoItemsList = response.body().getItems();
                        mVideoListAdapter.setItems(mVideoItemsList);
                        mVideoListAdapter.notifyDataSetChanged();
                        mLogoIv.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VideoListResponse> call, @NonNull Throwable t) {
                    mSwipe.setRefreshing(false);
                    mLogoIv.setVisibility(View.VISIBLE);
                    Log.e(TAG, "error" + t);
                    Toast.makeText(HomeActivity.this, R.string.network_request_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }else { // No Internet Connection
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

    @Override
    public void onItemClicked(Item item) {
        Intent intent = new Intent(this,VideoPlayerActivity.class);
        intent.putExtra(Consts.VIDEO_ITEM,item);
        startActivity(intent);
    }
}