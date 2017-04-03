package com.albineli.udacity.popularmovies.moviedetail;

import com.albineli.udacity.popularmovies.model.MovieModel;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class MovieDetailContract {
    public interface View {
        void setTitle();
        void showMovieDetail(MovieModel movieModel);
    }

    public interface Presenter {
        void start(MovieModel movieModel);
    }
}
