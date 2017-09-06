package com.udacity.bakingtime.recipedetail.ingredientlist;


import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.model.RecipeIngredientModel;

import java.util.List;

public abstract class RecipeIngredientListContract {
    public interface View {

        void showIngredientList(List<RecipeIngredientModel> recipeIngredientList);
    }

    public interface Presenter extends BasePresenter<View> {
        void start(List<RecipeIngredientModel> recipeIngredientList);
    }
}
