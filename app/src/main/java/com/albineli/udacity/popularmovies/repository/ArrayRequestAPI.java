package com.albineli.udacity.popularmovies.repository;

import com.google.gson.annotations.SerializedName;

public class ArrayRequestAPI<T> {
    @SerializedName("results")
    public T results;

    @SerializedName("page")
    public int page;
}
