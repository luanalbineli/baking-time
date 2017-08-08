package com.albineli.udacity.popularmovies.recipelist;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeModel;

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

        void openRecipeDetail(int position, RecipeModel recipeModel);
    }
}
