package com.essam.youtubeplayer.model;

import java.io.Serializable;

public class Statistic implements Serializable {
   private String viewCount = "0";
   private String likeCount = "0";
   private String dislikeCount = "0";

    public Statistic() {
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
