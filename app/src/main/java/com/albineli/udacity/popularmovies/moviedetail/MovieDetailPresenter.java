package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import javax.inject.Inject;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;

    @Inject
    public MovieDetailPresenter(@NonNull MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void start(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);
    }

    @Override
    public void setView(MovieDetailContract.View view) {
        mView = view;
    }
}
