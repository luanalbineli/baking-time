package com.albineli.udacity.popularmovies.recipedetail.trailer;

import java.util.List;

import javax.inject.Inject;

public class MovieTrailerListDialogPresenter implements MovieTrailerListDialogContract.Presenter {
    private MovieTrailerListDialogContract.View mView;

    @Inject
    public MovieTrailerListDialogPresenter() { }

    @Override
    public void setView(MovieTrailerListDialogContract.View view) {
        mView = view;
    }


    @Override
    public void start(List<MovieTrailerModel> movieTrailerList) {
        mView.showTrailersIntoList(movieTrailerList);
    }
}
