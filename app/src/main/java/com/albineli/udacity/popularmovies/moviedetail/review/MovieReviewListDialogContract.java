package com.albineli.udacity.popularmovies.moviedetail.review;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;

import java.util.List;

class MovieReviewListDialogContract {
    private MovieReviewListDialogContract() {}

    interface View {
        void addReviewsToList(List<MovieReviewModel> movieReviewList);

        void enableLoadMoreListener();

        void disableLoadMoreListener();

        void showLoadingIndicator();

        void showErrorLoadingReviews();
    }

    interface Presenter extends BasePresenter<View> {
        void start(List<MovieReviewModel> movieReviewList, int movieId, boolean hasMore);

        void onListEndReached();
    }
}
