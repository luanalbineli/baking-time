package com.udacity.bakingtime.recipedetail.ingredientlist;


import android.support.annotation.NonNull;


import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class RecipeIngredientListPresenter extends BasePresenterImpl implements RecipeIngredientListContract.Presenter {
    private RecipeIngredientListContract.View mView;

    @Inject
    RecipeIngredientListPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void setView(RecipeIngredientListContract.View view) {
        mView = view;
    }

    @Override
    public void start(List<RecipeIngredientModel> recipeIngredientList) {
        mView.showIngredientList(recipeIngredientList);
    }
}
