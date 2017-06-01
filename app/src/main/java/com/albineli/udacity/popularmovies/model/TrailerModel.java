package com.albineli.udacity.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class TrailerModel {
    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("key")
    private String key;

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }
}
