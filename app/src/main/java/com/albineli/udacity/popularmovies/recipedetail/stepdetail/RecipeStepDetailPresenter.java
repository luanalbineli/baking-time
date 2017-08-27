package com.albineli.udacity.popularmovies.recipedetail.stepdetail;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeStepDetailPresenter extends BasePresenterImpl implements RecipeStepDetailContract.Presenter {
    private RecipeStepDetailContract.View mView;

    @Inject
    RecipeStepDetailPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void setView(RecipeStepDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start(@Nullable RecipeModel recipeModel) {
    }

    @Override
    public void onSelectStep(RecipeStepModel recipeStepModel) {
        mView.showStepDetail(recipeStepModel);
    }
}
