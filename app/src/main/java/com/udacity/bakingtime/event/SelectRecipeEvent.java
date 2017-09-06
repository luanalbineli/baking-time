package com.udacity.bakingtime.event;

import com.udacity.bakingtime.model.RecipeModel;

public class SelectRecipeEvent {
    public final RecipeModel recipeModel;
    public SelectRecipeEvent(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }
}
