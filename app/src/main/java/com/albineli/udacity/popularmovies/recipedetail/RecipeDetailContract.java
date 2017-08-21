package com.albineli.udacity.popularmovies.recipedetail;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeModel;

import java.util.List;

/**
 * Presenter of the Movie Detail Fragment.
 */

public abstract class RecipeDetailContract {
    public interface View {

        void showRecipeDetailContent(RecipeModel recipeModel);
    }

    public interface Presenter extends BasePresenter<View> {

        void start(RecipeModel mRecipeModel);
    }
}
