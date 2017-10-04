package com.udacity.bakingtime.injector.components;


import com.udacity.bakingtime.injector.PerActivity;
import com.udacity.bakingtime.recipelist.RecipeListActivity;
import com.udacity.bakingtime.widget.RecipeSelectActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(RecipeListActivity mainActivity);
    void inject(RecipeSelectActivity recipeSelectActivity);
}
