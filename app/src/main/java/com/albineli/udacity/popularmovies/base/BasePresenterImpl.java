package com.albineli.udacity.popularmovies.base;

import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;


public abstract class BasePresenterImpl {
    protected final MovieRepository mMovieRepository;

    public BasePresenterImpl(MovieRepository movieRepository) {
        this.mMovieRepository = movieRepository;
    }
}
