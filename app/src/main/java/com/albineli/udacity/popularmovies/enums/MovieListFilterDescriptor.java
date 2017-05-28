package com.albineli.udacity.popularmovies.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;

public class MovieListFilterDescriptor {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POPULAR, RATING, FAVORITE})
    public @interface MovieListFilter {}

    public static final int POPULAR = 0;
    public static final int RATING = 1;
    public static final int FAVORITE = 2;

    private MovieListFilterDescriptor() {}

    public static @MovieListFilterDescriptor.MovieListFilter int parseFromInt(int supposedMovieListFilter) {
        switch (supposedMovieListFilter) {
            case POPULAR:
                return POPULAR;
            case RATING:
                return RATING;
            case FAVORITE:
                return FAVORITE;
        }
        throw new InvalidParameterException("supposedMovieListFilter");
    }
}
