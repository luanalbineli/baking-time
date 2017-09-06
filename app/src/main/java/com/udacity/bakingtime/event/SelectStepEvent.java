package com.udacity.bakingtime.event;


import com.udacity.bakingtime.model.RecipeStepModel;

public class SelectStepEvent {
    public final RecipeStepModel recipeStepModel;

    public SelectStepEvent(RecipeStepModel recipeStepModel) {
        this.recipeStepModel = recipeStepModel;
    }
}
