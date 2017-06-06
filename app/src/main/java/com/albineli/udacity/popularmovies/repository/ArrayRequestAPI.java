package com.albineli.udacity.popularmovies.repository;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArrayRequestAPI<T> {
    @SerializedName("results")
    public List<T> results;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    public boolean hasMorePages() {
        return page < totalPages;
    }
}
