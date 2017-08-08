package com.albineli.udacity.popularmovies.recipedetail.ingredientlist;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeIngredientModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

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

    }
}
