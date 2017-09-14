package com.udacity.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.mainactivity.MainActivity;
import com.udacity.bakingtime.model.RecipeModel;

public abstract class RecipeWidgetManager {
    public static void bindLayout(AppWidgetManager appWidgetManager, Context context, int widgetId, RecipeModel recipeModel) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_final_layout);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(MainActivity.VIEW_RECIPE_DETAIL_ACTION);
        intent.putExtra(MainActivity.RECIPE_BUNDLE_KEY, recipeModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, MainActivity.VIEW_RECIPE_DETAIL_REQUEST_CODE, intent, 0);

        views.setTextViewText(R.id.tvWidgetRecipeDescription, recipeModel.getName());
        views.setOnClickPendingIntent(R.id.tvWidgetRecipeDescription, pendingIntent);

        views.setOnClickPendingIntent(R.id.ivWidgetRecipeImage, pendingIntent);
        if (TextUtils.isEmpty(recipeModel.getImage())) {
            views.setImageViewResource(R.id.ivWidgetRecipeImage, R.drawable.default_image_widget);
        } else {
            views.setImageViewUri(R.id.ivWidgetRecipeImage, Uri.parse(recipeModel.getImage()));
        }

        appWidgetManager.updateAppWidget(widgetId, views);
    }
}
