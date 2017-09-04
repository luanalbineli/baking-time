package com.albineli.udacity.popularmovies.recipedetail.stepdetail;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class RecipeStepDetailContract {
    public interface View {
        void showStepDetail(RecipeStepModel recipeStepModel);
    }

    public interface Presenter extends BasePresenter<View> {

        void start(RecipeModel mRecipeModel);

        void onSelectStep(RecipeStepModel recipeStepModel);
    }
}
