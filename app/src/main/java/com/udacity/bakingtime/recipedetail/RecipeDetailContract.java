package com.udacity.bakingtime.recipedetail;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.event.SelectStepEvent;
import com.udacity.bakingtime.model.RecipeDetailViewModel;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.model.RecipeStepModel;

import java.util.List;

public abstract class RecipeDetailContract {
    public interface View {
        void showIngredientList(List<RecipeIngredientModel> ingredientList);

        void configureMasterDetail(RecipeModel recipeModel, int selectedStepIndex, RecipeStepModel recipeStepModel);

        void configureNormalLayout(RecipeModel recipeModel);

        void showStepDetail(RecipeStepModel recipeStepModel);
    }

    public interface Presenter extends BasePresenter<View> {

        void start(RecipeDetailViewModel recipeDetailViewModel);

        void showIngredientList();

        void onDismissIngredientList();

        void onSelectStepEvent(SelectStepEvent selectStepEvent);
    }
}
