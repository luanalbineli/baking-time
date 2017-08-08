package com.albineli.udacity.popularmovies.recipedetail.ingredientlist;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.model.RecipeIngredientModel;
import com.albineli.udacity.popularmovies.model.RecipeModel;

import java.util.List;

public abstract class RecipeIngredientListContract {
    public interface View {

    }

    public interface Presenter extends BasePresenter<View> {
        void start(List<RecipeIngredientModel> recipeIngredientList);
    }
}
