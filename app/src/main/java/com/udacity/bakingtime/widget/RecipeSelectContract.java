package com.udacity.bakingtime.widget;

import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

public interface RecipeSelectContract {
    interface View {

        void showRecipeList(List<RecipeModel> recipeList);

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showErrorLoadingRecipeList();
    }

    interface Presenter extends BasePresenter<View> {

        void start();

        void tryAgain();
    }
}
