package com.udacity.bakingtime.recipedetail;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeModel;

public abstract class RecipeDetailContract {
    public interface View {

        void showRecipeDetailContent(RecipeModel recipeModel);
    }

    public interface Presenter extends BasePresenter<View> {

        void start(RecipeModel mRecipeModel);
    }
}
