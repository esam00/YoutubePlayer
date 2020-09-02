package com.essam.youtubeplayer.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListResponse {
    @SerializedName("items")
    private List<Item> mItems;

    public VideoListResponse() {
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public String toString() {
        return "VideoListResponse{" +
                "items size=" + mItems.size() +
                '}';
    }
}
