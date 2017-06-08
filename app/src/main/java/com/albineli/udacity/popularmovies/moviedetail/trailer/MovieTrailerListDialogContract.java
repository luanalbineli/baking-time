package com.albineli.udacity.popularmovies.moviedetail.trailer;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;

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
