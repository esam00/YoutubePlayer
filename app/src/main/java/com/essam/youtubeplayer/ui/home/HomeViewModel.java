package com.essam.youtubeplayer.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.essam.youtubeplayer.Api.ApiClient;
import com.essam.youtubeplayer.model.VideoListResponse;
import com.essam.youtubeplayer.utils.Consts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();
    MutableLiveData<VideoListResponse> mVideoListResponseMutableLiveData = new MutableLiveData<>();

    /**
     * This method simply creates a Retrofit instance and make a network call to Youtube Data Api
     * and returns a list of videos that match the API request parameters.
     * For more details checkout the documentation : https://developers.google.com/youtube/v3/docs/videos/list
     */
    public void getTrendingVideos(){
        Log.i(TAG, "Api request >>>>>>> getVideos" );
        ApiClient.getINSTANCE().getTrendingVideos(
                Consts.PART_QUERY_VALUE,
                Consts.CHART_QUERY_VALUE,
                Consts.REGION_CODE_QUERY_VALUE,
                Consts.MAX_RESULT_QUERY_VALUE,
                Consts.GOOGLE_API_KEY
        ).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoListResponse> call,@NonNull Response<VideoListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "Get videos Response : " + response);
                    mVideoListResponseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<VideoListResponse> call,@NonNull Throwable t) {
                Log.e(TAG, "error" + t);
                mVideoListResponseMutableLiveData.setValue(null);
            }
        });
    }
}
