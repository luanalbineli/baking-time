package com.albineli.udacity.popularmovies.base;

import com.albineli.udacity.popularmovies.repository.RecipeRepository;


public abstract class BasePresenterImpl {
    protected final RecipeRepository mMovieRepository;

    public BasePresenterImpl(RecipeRepository movieRepository) {
        this.mMovieRepository = movieRepository;
    }
}
