package com.udacity.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.ingredientlist.IngredientListAdapter;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

import timber.log.Timber;


public class RecipeIngredientListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this, intent);
    }

    public static class WidgetRemoteViewsFactory implements RemoteViewsFactory {
        private final Context mContext;
        private final int mAppWidgetId;

        @Inject
        RecipeRepository mRecipeRepository;
        private RecipeModel mRecipeModel;

        WidgetRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            Timber.i("WidgetRemoteViewsFactory - WidgetId: " + mAppWidgetId);
        }

        @Override
        public void onCreate() {
            Timber.i("WidgetRemoteViewsFactory - onCreate()");
            DaggerFragmentComponent.builder()
                    .applicationComponent(((BakingTimeApplication) mContext.getApplicationContext()).getApplicationComponent())
                    .build()
                    .inject(this);
            Timber.i("WidgetRemoteViewsFactory - onCreate() DONE!");
        }

        @Override
        public void onDataSetChanged() {
            mRecipeModel = mRecipeRepository.getCachedRecipeByWidgetId(mAppWidgetId);
            Timber.i("WidgetRemoteViewsFactory - recipe model: " + mRecipeModel);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            Timber.i("WidgetRemoteViewsFactory - getCount(): " + mRecipeModel.getIngredientList().size());
            return mRecipeModel.getIngredientList().size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RecipeIngredientModel ingredientModel = mRecipeModel.getIngredientList().get(position);
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item_widget);

            String formattedQuantity = IngredientListAdapter.QUANTITY_FORMAT.format(ingredientModel.getQuantity());
            remoteViews.setTextViewText(R.id.tvRecipeIngredientQuantity, formattedQuantity);

            remoteViews.setTextViewText(R.id.tvRecipeIngredientMeasurementUnit, ingredientModel.getMeasure());

            remoteViews.setTextViewText(R.id.tvRecipeIngredientName, ingredientModel.getIngredient());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
