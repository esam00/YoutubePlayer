package com.essam.youtubeplayer.utils;

public interface Consts {
    //keys
    String GOOGLE_API_KEY = "AIzaSyDMKCo4gOk51rLf9-JqldunsVI0HYjW-GQ";

    //apis & query parameters
    String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    String TRENDING = "videos";
    String PART_QUERY_VALUE = "snippet,statistics";
    String CHART_QUERY_VALUE = "mostPopular";
    String REGION_CODE_QUERY_VALUE = "KW";
    int MAX_RESULT_QUERY_VALUE = 50;

    //constants
    String VIDEO_ITEM = "com.essam.youtubeplayer.VIDEO_ITEM" ;
}
