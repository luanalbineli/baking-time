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
    }

    public interface Presenter extends BasePresenter<View> {

        void start(@Nullable List<RecipeStepModel> recipeStepList);

        void handleStepVideoClick(RecipeStepModel recipeStepModel);
    }
}
