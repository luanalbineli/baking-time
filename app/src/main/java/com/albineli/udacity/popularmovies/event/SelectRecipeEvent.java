package com.albineli.udacity.popularmovies.event;


import com.albineli.udacity.popularmovies.model.RecipeModel;

public class SelectRecipeEvent {
    private final RecipeModel recipeModel;

    public SelectRecipeEvent(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }
}
