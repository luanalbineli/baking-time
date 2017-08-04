package com.albineli.udacity.popularmovies.base;

import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;


public abstract class BasePresenterImpl {
    protected final RecipeRepository mRecipeRepository;

    public BasePresenterImpl(RecipeRepository movieRepository) {
        this.mRecipeRepository = movieRepository;
    }

    public abstract void openRecipeDetail(int position, RecipeModel recipeModel);
}
