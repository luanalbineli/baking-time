package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import javax.inject.Inject;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View mView;

    @Inject
    public MovieDetailPresenter(@NonNull MovieDetailContract.View view, @NonNull MovieRepository movieRepository) {
        super(movieRepository);
        mView = view;
    }

    @Override
    public void start(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);
    }
}
