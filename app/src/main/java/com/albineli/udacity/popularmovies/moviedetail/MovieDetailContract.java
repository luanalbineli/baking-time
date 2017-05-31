package com.albineli.udacity.popularmovies.moviedetail;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.MovieModel;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class MovieDetailContract {
    public interface View {
        void setTitle();
        void showMovieDetail(MovieModel movieModel);

        void setFavoriteButtonState(boolean favorite);

        void showSuccessMessageAddFavoriteMovie();

        void showSuccessMessageRemoveFavoriteMovie();


        void showErrorMessageAddFavoriteMovie();

        void showErrorMessageRemoveFavoriteMovie();
    }

    public interface Presenter extends BasePresenter<View> {
        void start(MovieModel movieModel);

        void removeFavoriteMovie(MovieModel movieModel);

        void saveFavoriteMovie(MovieModel movieModel);
    }
}
