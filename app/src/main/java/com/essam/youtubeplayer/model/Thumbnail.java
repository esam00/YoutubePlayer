package com.essam.youtubeplayer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnail implements Serializable {
    @SerializedName("standard")
    private StandardThumbnail mStandardThumbnail;

    public Thumbnail() {
    }

    public StandardThumbnail getStandardThumbnail() {
        return mStandardThumbnail;
    }

    public void setStandardThumbnail(StandardThumbnail standardThumbnail) {
        mStandardThumbnail = standardThumbnail;
    }
}
