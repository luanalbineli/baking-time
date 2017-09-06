package com.udacity.bakingtime.recipedetail.stepdetail;

import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.model.RecipeStepModel;

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
