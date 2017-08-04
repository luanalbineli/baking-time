package com.albineli.udacity.popularmovies.recipedetail;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeModel;

import java.util.List;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class MovieDetailContract {
    public interface View {
        void showMovieReview(List<MovieReviewModel> movieReviewModelList);

        void showMovieDetail(RecipeModel movieModel);

        void showMovieTrailer(List<MovieTrailerModel> movieTrailerList);

        void setFavoriteButtonState(boolean favorite);

        void showSuccessMessageAddFavoriteMovie();

        void showSuccessMessageRemoveFavoriteMovie();


        void showErrorMessageAddFavoriteMovie();

        void showErrorMessageRemoveFavoriteMovie();

        void showErrorMessageLoadReviews();

        void showErrorMessageLoadTrailers();

        void setShowAllReviewsButtonVisibility(boolean visible);

        void setShowAllTrailersButtonVisibility(boolean visible);

        void showLoadingReviewsIndicator();

        void showLoadingTrailersIndicator();

        void showAllReviews(List<MovieReviewModel> movieReviewList, boolean hasMore);

        void showAllTrailers(List<MovieTrailerModel> movieTrailerList);

        void showEmptyReviewListMessage();

        void showEmptyTrailerListMessage();
    }

    public interface Presenter extends BasePresenter<View> {
        void start(RecipeModel movieModel);

        void removeFavoriteMovie(RecipeModel movieModel);

        void saveFavoriteMovie(RecipeModel movieModel);

        void showAllReviews();

        void showAllTrailers();

        void tryToLoadTrailersAgain();

        void tryToLoadReviewAgain();
    }
}
