package com.udacity.bakingtime.mainactivity;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

public interface RecipeListContract {
    interface View {

        void hideLoadingIndicator();

        void showLoadingIndicator();

        void showRecipeList(List<RecipeModel> recipeList);

        void showErrorLoadingRecipeList(Throwable error);

        void goToRecipeDetail(RecipeModel recipeModel);
    }

    interface Presenter extends BasePresenter<View> {
        void start();

        void handleSelectRecipeEvent(RecipeModel recipeModel);
    }
}
