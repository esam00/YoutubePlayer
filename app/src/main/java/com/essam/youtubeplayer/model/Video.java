package com.essam.youtubeplayer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("thumbnails")
    private Thumbnail thumbnail;

    public Video() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
