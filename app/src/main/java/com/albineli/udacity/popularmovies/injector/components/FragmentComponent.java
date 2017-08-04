package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.recipedetail.MovieDetailFragment;
import com.albineli.udacity.popularmovies.recipelist.RecipeListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    void inject(MovieDetailFragment fragment);
    void inject(RecipeListFragment fragment);
}
