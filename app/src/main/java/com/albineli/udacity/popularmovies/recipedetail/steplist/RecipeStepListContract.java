package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.support.annotation.Nullable;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;

import java.util.List;

/**
 * Presenter of the Recipe Step List.
 */

public abstract class RecipeStepListContract {
    public interface View {

        void showStepList(List<RecipeStepModel> recipeStepList);

        void openStepVideo(String videoUrl);

        void viewStepDetail(RecipeStepModel recipeStepModel);

        void setSelectedRecipeStep(int selectedRecipeStepIndex);

        void clearSelectedStep();
    }

    public interface Presenter extends BasePresenter<View> {

        void start(@Nullable List<RecipeStepModel> recipeStepList);

        void handleStepVideoClick(int selectedRecipeStepIndex, RecipeStepModel recipeStepModel, boolean viewStepDetail);

        void handleRecipeStepList(List<RecipeStepModel> recipeStepList);
    }
}
