package com.essam.youtubeplayer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StandardThumbnail implements Serializable {
    @SerializedName("url")
    private String url;

    public StandardThumbnail() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
