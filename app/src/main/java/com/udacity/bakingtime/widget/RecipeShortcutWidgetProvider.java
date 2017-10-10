package com.udacity.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

import timber.log.Timber;


public class RecipeShortcutWidgetProvider extends AppWidgetProvider {
    @Inject
    RecipeRepository mRecipeRepository;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.d("Updating the widgets. Count: " + appWidgetIds.length);
        DaggerFragmentComponent.builder()
                .applicationComponent(((BakingTimeApplication) context.getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);

        for (int widgetId : appWidgetIds) {
            RecipeModel recipeModel = mRecipeRepository.getCachedRecipeByWidgetId(widgetId);
            Timber.d("Checking if there is a recipe model for it: " + recipeModel);
            if (recipeModel != null) { // TODO: Handle if the recipe was not found.
                RecipeWidgetManager.bindLayout(appWidgetManager, context, widgetId, recipeModel);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        DaggerFragmentComponent.builder()
                .applicationComponent(((BakingTimeApplication) context.getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);

        for (int widgetId : appWidgetIds) {
            mRecipeRepository.removeRecipeIdByWidgetId(widgetId);
        }
    }
}
