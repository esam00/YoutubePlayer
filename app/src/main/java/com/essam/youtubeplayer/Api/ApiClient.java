package com.essam.youtubeplayer.Api;
import com.essam.youtubeplayer.model.VideoListResponse;
import com.essam.youtubeplayer.utils.Consts;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiService mApiService;
    private static ApiClient INSTANCE;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static ApiClient getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public Call<VideoListResponse> getTrendingVideos(String part, String chart, String regionCode,
                                                     int maxResults, String apiKy){
        return mApiService.getVideos(part,chart,regionCode,maxResults,apiKy);
    }
}
