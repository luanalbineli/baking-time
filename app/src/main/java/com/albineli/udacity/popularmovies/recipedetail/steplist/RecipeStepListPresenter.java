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
    private int mSelectedStepIndex = -1;

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
    public void handleStepVideoClick(int selectedRecipeStepIndex, RecipeStepModel recipeStepModel, boolean viewStepDetail) {
        if (viewStepDetail) {
            if (mSelectedStepIndex != selectedRecipeStepIndex) { // only notify the detail if the user change another step (different from the current).
                selectAndShowRecipeStep(selectedRecipeStepIndex, recipeStepModel);
            }
            return;
        }
        mView.openStepVideo(recipeStepModel.getRealVideoUrl());

    }

    @Override
    public void handleRecipeStepList(List<RecipeStepModel> recipeStepList) {
        if (recipeStepList != null) {
            mView.showStepList(recipeStepList);
            if (recipeStepList.size() > 0) {
                selectAndShowRecipeStep(0, recipeStepList.get(0));
            }
        }
    }

    private void selectAndShowRecipeStep(int selectedRecipeStepIndex, RecipeStepModel recipeStepModel) {
        mView.viewStepDetail(recipeStepModel);
        if (mSelectedStepIndex != -1) {
            mView.clearSelectedStep();
        }

        mView.setSelectedRecipeStep(selectedRecipeStepIndex);
        mSelectedStepIndex = selectedRecipeStepIndex;
    }
}
