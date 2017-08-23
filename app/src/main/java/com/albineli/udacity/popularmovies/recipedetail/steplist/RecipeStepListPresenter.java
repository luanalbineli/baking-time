package com.albineli.udacity.popularmovies.recipedetail.steplist;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class RecipeStepListPresenter extends BasePresenterImpl implements RecipeStepListContract.Presenter {
    private RecipeStepListContract.View mView;

    @Inject
    RecipeStepListPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }


    @Override
    public void setView(RecipeStepListContract.View view) {
        mView = view;
    }

    @Override
    public void start(@Nullable List<RecipeStepModel> recipeStepList) {
        if (recipeStepList != null) {
            mView.showStepList(recipeStepList);
        }
    }

    @Override
    public void handleStepVideoClick(RecipeStepModel recipeStepModel) {
        mView.openStepVideo(recipeStepModel.getRealVideoUrl());
    }
}
