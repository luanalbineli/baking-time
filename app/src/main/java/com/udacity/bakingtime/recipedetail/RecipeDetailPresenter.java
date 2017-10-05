package com.udacity.bakingtime.recipedetail;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeDetailPresenter extends BasePresenterImpl implements RecipeDetailContract.Presenter {
    private RecipeDetailContract.View mView;

    private RecipeModel mRecipeModel;

    @Inject
    RecipeDetailPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void setView(RecipeDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start(@Nullable RecipeModel recipeModel) {
        mRecipeModel = recipeModel;
        if (recipeModel != null) {
            mView.showRecipeDetailContent(recipeModel);
        }
    }

    @Override
    public void showIngredientList() {
        mView.showIngredientList(mRecipeModel.getIngredientList());
    }
}
