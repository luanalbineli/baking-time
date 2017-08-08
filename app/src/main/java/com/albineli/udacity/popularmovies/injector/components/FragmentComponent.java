package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.recipedetail.RecipeDetailFragment;
import com.albineli.udacity.popularmovies.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.albineli.udacity.popularmovies.recipedetail.steplist.RecipeStepListFragment;
import com.albineli.udacity.popularmovies.recipelist.RecipeListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    void inject(RecipeDetailFragment fragment);
    void inject(RecipeListFragment fragment);
    void inject(RecipeStepListFragment fragment);
    void inject(RecipeIngredientListFragment fragment);
}
