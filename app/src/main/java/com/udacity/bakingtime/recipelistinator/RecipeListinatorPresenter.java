package com.udacity.bakingtime.recipelistinator;

import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

import javax.inject.Inject;

public class RecipeListinatorPresenter implements RecipeListinatorContract.Presenter {
    private RecipeListinatorContract.View mView;

    @Inject
    RecipeListinatorPresenter() {
    }

    @Override
    public void setView(RecipeListinatorContract.View view) {
        mView = view;
    }

    @Override
    public void showRecipeList(List<RecipeModel> recipeList) {
        mView.showRecipeList(recipeList);
    }

    @Override
    public void handleRecipeItemClick(int position, RecipeModel recipeModel) {
        mView.onClickRecipeItem(recipeModel);
    }
}
