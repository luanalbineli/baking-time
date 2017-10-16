package com.udacity.bakingtime.injector.components;


import com.udacity.bakingtime.injector.PerFragment;
import com.udacity.bakingtime.recipelist.RecipeListActivity;
import com.udacity.bakingtime.recipedetail.RecipeDetailActivity;
import com.udacity.bakingtime.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.udacity.bakingtime.recipedetail.stepdetail.RecipeStepDetailFragment;
import com.udacity.bakingtime.recipedetail.steplist.RecipeStepListFragment;
import com.udacity.bakingtime.recipelistinator.RecipeListinatorFragment;
import com.udacity.bakingtime.widget.RecipeIngredientListViewService;
import com.udacity.bakingtime.widget.RecipeShortcutWidgetProvider;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    void inject(RecipeDetailActivity fragment);

    void inject(RecipeListActivity fragment);

    void inject(RecipeStepListFragment fragment);

    void inject(RecipeIngredientListFragment fragment);

    void inject(RecipeStepDetailFragment fragment);

    void inject(RecipeListinatorFragment fragment);

    void inject(RecipeShortcutWidgetProvider recipeShortcutWidgetProvider);

    void inject(RecipeIngredientListViewService.WidgetRemoteViewsFactory widgetRemoteViewsFactory);
}
