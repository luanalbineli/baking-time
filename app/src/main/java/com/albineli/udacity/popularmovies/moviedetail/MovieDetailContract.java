package com.albineli.udacity.popularmovies.moviedetail;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;

import java.util.List;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class MovieDetailContract {
    public interface View {
        void showMovieReview(List<MovieReviewModel> movieReviewModelList);

        void showMovieDetail(MovieModel movieModel);

        void setFavoriteButtonState(boolean favorite);

        void showSuccessMessageAddFavoriteMovie();

        void showSuccessMessageRemoveFavoriteMovie();


        void showErrorMessageAddFavoriteMovie();

        void showErrorMessageRemoveFavoriteMovie();

        void showErrorMessageLoadReviews();

        void setShowAllReviewsButtonVisibility(boolean visible);

        void showLoadingReviewsIndicator();

        void showAllReviews(List<MovieReviewModel> mMovieReviewList);
    }

    public interface Presenter extends BasePresenter<View> {
        void start(MovieModel movieModel);

        void removeFavoriteMovie(MovieModel movieModel);

        void saveFavoriteMovie(MovieModel movieModel);

        void showAllReviews();
    }
}
