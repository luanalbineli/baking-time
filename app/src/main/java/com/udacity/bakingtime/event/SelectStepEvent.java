package com.udacity.bakingtime.event;


import com.udacity.bakingtime.model.RecipeStepModel;

public class SelectStepEvent {
    public final RecipeStepModel recipeStepModel;
    public final int selectedIndex;

    public SelectStepEvent(int selectedIndex, RecipeStepModel recipeStepModel) {
        this.selectedIndex = selectedIndex;
        this.recipeStepModel = recipeStepModel;
    }
}
