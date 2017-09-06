package com.udacity.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.event.SelectRecipeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import timber.log.Timber;

public class RecipeSelectActivity extends AppCompatActivity {

    private int mShortcutWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_select);
        Timber.d("Initialized the recipe select activity");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mShortcutWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Timber.d("Has extras. WidgetId: " + mShortcutWidgetId + ". Is invalid? " + (mShortcutWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID));
        }

        if (mShortcutWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mShortcutWidgetId);
        setResult(RESULT_CANCELED, resultValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectRecipeEvent(SelectRecipeEvent selectRecipeEvent) {
        Timber.i("RECIPE_SELECT_ACTIVITY - Selected a recipe: " + selectRecipeEvent);
    }
}
