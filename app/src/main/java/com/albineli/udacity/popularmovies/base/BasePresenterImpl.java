package com.albineli.udacity.popularmovies.base;

import com.albineli.udacity.popularmovies.repository.RecipeRepository;


public abstract class BasePresenterImpl {
    protected final RecipeRepository mRecipeRepository;

    public BasePresenterImpl(RecipeRepository recipeRepository) {
        this.mRecipeRepository = recipeRepository;
    }
}
