package com.essam.youtubeplayer.Api;

import com.essam.youtubeplayer.utils.Consts;
import com.essam.youtubeplayer.model.VideoListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Consts.TRENDING)
    Call<VideoListResponse> getVideos(@Query("part") String part,
                                      @Query("chart") String chart,
                                      @Query("regionCode") String regionCode,
                                      @Query("maxResults") int maxResults,
                                      @Query("key") String key);
}