package com.udacity.bakingtime.model;


public class RecipeDetailViewModel {
    public final RecipeModel recipeModel;
    public Integer selectedStepIndex;
    public Boolean ingredientListDialogDismissed;
    public final boolean useMasterDetail;

    public RecipeDetailViewModel(RecipeModel recipeModel, Integer selectedStepIndex, Boolean ingredientListDialogDismissed, boolean useMasterDetail) {
        this.recipeModel = recipeModel;
        this.selectedStepIndex = selectedStepIndex;
        this.ingredientListDialogDismissed = ingredientListDialogDismissed;
        this.useMasterDetail = useMasterDetail;
    }

    @Override
    public String toString() {
        return "recipeModel: " + recipeModel
                + "\nselectedStepIndex: " + selectedStepIndex
                + "\ningredientListDialogDismissed: " + ingredientListDialogDismissed
                + "\nuseMasterDetail: " + useMasterDetail;
    }
}
