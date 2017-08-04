package com.albineli.udacity.popularmovies.recipedetail.trailer;

import com.albineli.udacity.popularmovies.base.BasePresenter;

import java.util.List;

class MovieTrailerListDialogContract {
    private MovieTrailerListDialogContract() {}

    interface View {
        void showTrailersIntoList(List<MovieTrailerModel> movieReviewList);
    }

    interface Presenter extends BasePresenter<View> {
        void start(List<MovieTrailerModel> movieReviewList);
    }
}
