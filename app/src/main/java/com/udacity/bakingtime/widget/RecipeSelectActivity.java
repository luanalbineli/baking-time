package com.udacity.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseActivity;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerActivityComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipelistinator.RecipeListinatorFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class RecipeSelectActivity extends BaseActivity<RecipeSelectContract.View> implements RecipeSelectContract.View {

    private int mShortcutWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private RecipeListinatorFragment mRecipeListinatorFragment;

    @Inject
    RecipeSelectPresenter mPresenter;

    @Override
    protected BasePresenter<RecipeSelectContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeSelectContract.View getViewImplementation() {
        return this;
    }

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

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

        configureLayout();
    }

    private void configureLayout() {
        mRecipeListinatorFragment = (RecipeListinatorFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRecipeListinator);
        Timber.i("mRecipeListinatorFragment - " + mRecipeListinatorFragment);
        mRecipeListinatorFragment.setTryAgainClickListener(mPresenter::tryAgain);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    public void showRecipeList(List<RecipeModel> recipeList) {
        mRecipeListinatorFragment.addRecipeList(recipeList);
    }

    @Override
    public void showLoadingIndicator() {
        mRecipeListinatorFragment.showLoadingIndicator();
    }

    @Override
    public void hideLoadingIndicator() {
        mRecipeListinatorFragment.hideLoadingIndicator();
    }

    @Override
    public void showErrorLoadingRecipeList() {
        mRecipeListinatorFragment.showErrorLoadingRecipeList();
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
