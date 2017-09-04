package com.albineli.udacity.popularmovies.event;


import com.albineli.udacity.popularmovies.model.RecipeStepModel;

public class SelectStepEvent {
    public final RecipeStepModel recipeStepModel;

    public SelectStepEvent(RecipeStepModel recipeStepModel) {
        this.recipeStepModel = recipeStepModel;
    }
}
