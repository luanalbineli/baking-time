package com.albineli.udacity.popularmovies.recipedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {
    private MovieDetailContract.View mView;
    private int mMovieId;

    @Inject
    MovieDetailPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void openRecipeDetail(int position, RecipeModel recipeModel) {

    }


    @Override
    public void setView(MovieDetailContract.View view) {

    }
}
