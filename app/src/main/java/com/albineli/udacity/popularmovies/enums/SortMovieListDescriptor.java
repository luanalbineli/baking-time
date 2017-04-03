package com.albineli.udacity.popularmovies.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SortMovieListDescriptor {
    public static final int POPULAR = 0;
    public static final int RATING = 1;

    public final int sortType;

    // Describes when the annotation will be discarded
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @IntDef({POPULAR, RATING})
    // Create an interface for validating int types
    public @interface SortMovieListDef {
    }

    public SortMovieListDescriptor(@SortMovieListDef int sortType) {
        this.sortType = sortType;
    }
}
