package com.essam.youtubeplayer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {
    @SerializedName("id")
     private String id;
    @SerializedName("snippet")
     private Video mVideo;
    @SerializedName("statistics")
    private Statistic mStatistic;

    public Item() {
    }

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Statistic getStatistic() {
        return mStatistic;
    }

    public void setStatistic(Statistic statistic) {
        mStatistic = statistic;
    }
}
