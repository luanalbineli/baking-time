package com.albineli.udacity.popularmovies.recipedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeDetailPresenter extends BasePresenterImpl implements RecipeDetailContract.Presenter {
    private RecipeDetailContract.View mView;

    @Inject
    RecipeDetailPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void setView(RecipeDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start(RecipeModel recipeModel) {

    }
}
