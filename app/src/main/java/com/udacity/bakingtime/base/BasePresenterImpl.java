package com.udacity.bakingtime.base;


import com.udacity.bakingtime.repository.RecipeRepository;

public abstract class BasePresenterImpl {
    protected final RecipeRepository mRecipeRepository;

    public BasePresenterImpl(RecipeRepository recipeRepository) {
        this.mRecipeRepository = recipeRepository;
    }
}
