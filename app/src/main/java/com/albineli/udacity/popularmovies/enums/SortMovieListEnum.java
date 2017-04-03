package com.albineli.udacity.popularmovies.enums;


import com.albineli.udacity.popularmovies.R;

public enum SortMovieListEnum {
    POPULAR(R.string.sortby_popular),
    RATING(R.string.sortby_rating);

    private final int descriptionRes;

    SortMovieListEnum(int descriptionRes) {
        this.descriptionRes = descriptionRes;
    }

    public int getDescriptionRes() {
        return descriptionRes;
    }
}
