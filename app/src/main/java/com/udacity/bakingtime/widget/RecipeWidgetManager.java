package com.udacity.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.RecipeDetailActivity;

import java.net.URL;

import timber.log.Timber;

abstract class RecipeWidgetManager {
    static void bindLayout(AppWidgetManager appWidgetManager, Context context, int widgetId, RecipeModel recipeModel) {
        Timber.d("bindLayout - Binding the layout for the widget: " + widgetId);
        Timber.d("bindLayout - recipeModel: " + recipeModel);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_final_layout);

        Intent intentWidgetClick = new Intent(context, RecipeDetailActivity.class);
        intentWidgetClick.setAction(String.valueOf(widgetId)); // For some reason, if we put the same action for the widgets, the first one overrides (recipeModel) the  the others' values.
        intentWidgetClick.putExtra(RecipeDetailActivity.RECIPE_MODEL_BUNDLE_KEY, recipeModel);

        PendingIntent pendingIntentWidgetClick = PendingIntent.getActivity(context, RecipeDetailActivity.VIEW_RECIPE_DETAIL_REQUEST_CODE, intentWidgetClick, 0);

        views.setTextViewText(R.id.tvWidgetRecipeName, recipeModel.getName());

        views.setOnClickPendingIntent(R.id.llWidgetRecipeContainer, pendingIntentWidgetClick);

        Intent intentAdapter = new Intent(context, RecipeIngredientListViewService.class);
        intentAdapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        views.setRemoteAdapter(R.id.lvWidgetIngredientList, intentAdapter);

        appWidgetManager.updateAppWidget(widgetId, views);
    }
}
