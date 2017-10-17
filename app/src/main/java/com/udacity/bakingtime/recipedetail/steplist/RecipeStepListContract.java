package com.udacity.bakingtime.recipedetail.steplist;

import android.support.annotation.Nullable;

import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeStepModel;

import java.util.List;


/**
 * Presenter of the Recipe Step List.
 */

public abstract class RecipeStepListContract {
    public interface View {

        void showStepList(List<RecipeStepModel> recipeStepList);

        void openStepVideo(String title, String videoUrl, String thumbnailUrl);

        void viewStepDetail(int selectedStepIndex, RecipeStepModel recipeStepModel);

        void setSelectedRecipeStep(int selectedRecipeStepIndex);

        void clearSelectedStep();
    }

    public interface Presenter extends BasePresenter<View> {

        void start(@Nullable List<RecipeStepModel> recipeStepList);

        void handleStepVideoClick(int selectedRecipeStepIndex, RecipeStepModel recipeStepModel, boolean viewStepDetail);

        void handleRecipeStepList(List<RecipeStepModel> recipeStepList);
    }
}
