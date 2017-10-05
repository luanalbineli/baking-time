package com.udacity.bakingtime.recipedetail;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

public abstract class RecipeDetailContract {
    public interface View {

        void showRecipeDetailContent(RecipeModel recipeModel);

        void showIngredientList(List<RecipeIngredientModel> ingredientList);
    }

    public interface Presenter extends BasePresenter<View> {

        void start(RecipeModel mRecipeModel);

        void showIngredientList();
    }
}
