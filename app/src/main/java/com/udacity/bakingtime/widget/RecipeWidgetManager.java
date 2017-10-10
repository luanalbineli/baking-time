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
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_final_layout);

        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.setAction(RecipeDetailActivity.VIEW_RECIPE_DETAIL_ACTION);
        intent.putExtra(RecipeDetailActivity.RECIPE_MODEL_BUNDLE_KEY, recipeModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, RecipeDetailActivity.VIEW_RECIPE_DETAIL_REQUEST_CODE, intent, 0);

        views.setTextViewText(R.id.tvWidgetRecipeDescription, recipeModel.getName());
        views.setOnClickPendingIntent(R.id.tvWidgetRecipeDescription, pendingIntent);

        views.setOnClickPendingIntent(R.id.ivWidgetRecipeImage, pendingIntent);
        if (TextUtils.isEmpty(recipeModel.getImage())) {
            views.setImageViewResource(R.id.ivWidgetRecipeImage, R.drawable.default_image_widget);
            appWidgetManager.updateAppWidget(widgetId, views);
        } else {
            new FetchWidgetImageAsyncTask(appWidgetManager, widgetId, views).execute(recipeModel.getImage());
        }
    }

    static class FetchWidgetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private final AppWidgetManager appWidgetManager;
        private final int widgetId;
        private final RemoteViews views;

        FetchWidgetImageAsyncTask(AppWidgetManager appWidgetManager, int widgetId, RemoteViews views) {
            this.appWidgetManager = appWidgetManager;
            this.widgetId = widgetId;
            this.views = views;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Timber.i("RecipeWidgetManager - 1 - Init the download");
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Timber.i("RecipeWidgetManager - Started the download");
            try {
                URL url = new URL(strings[0]);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                Timber.e("RecipeWidgetManager - An error occurred while tried to load the image");
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            Timber.e("RecipeWidgetManager - Finished the download: " + bitmap);
            if (bitmap == null) {
                views.setImageViewResource(R.id.ivWidgetRecipeImage, R.drawable.default_image_widget);
            } else {
                views.setImageViewBitmap(R.id.ivWidgetRecipeImage, bitmap);
            }
            appWidgetManager.updateAppWidget(widgetId, views);

        }
    }
}
