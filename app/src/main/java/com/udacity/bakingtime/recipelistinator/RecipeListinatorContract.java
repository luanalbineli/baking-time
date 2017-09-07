package com.udacity.bakingtime.recipelistinator;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

public interface RecipeListinatorContract {
    interface View {
        void showRecipeList(List<RecipeModel> recipeList);

        void onClickRecipeItem(RecipeModel recipeModel);
    }

    interface Presenter extends BasePresenter<View> {
        void showRecipeList(List<RecipeModel> recipeList);
        void handleRecipeItemClick(int position, RecipeModel recipeModel);
    }
}
