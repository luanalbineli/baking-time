package com.udacity.bakingtime.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseActivity;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.RecipeDetailActivity;
import com.udacity.bakingtime.recipelistinator.RecipeListinatorFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;


public class RecipeListActivity extends BaseActivity<RecipeListContract.View> implements RecipeListContract.View {
    private RecipeListinatorFragment mRecipeListinatorFragment;

    @Override
    protected BasePresenter<RecipeListContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeListContract.View getViewImplementation() {
        return this;
    }

    @Inject
    RecipeListPresenter mPresenter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecipeListinatorFragment = (RecipeListinatorFragment) getFragmentManager().findFragmentById(R.id.fragmentRecipeListinator);

        setTitle(R.string.recipe_list);

        mPresenter.start();
    }

    @Override
    public void goToRecipeDetail(RecipeModel recipeModel) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_MODEL_BUNDLE_KEY, recipeModel);

        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectRecipeEvent(SelectRecipeEvent selectRecipeEvent) {
        mPresenter.handleSelectRecipeEvent(selectRecipeEvent.recipeModel);
    }

    @Override
    public void hideLoadingIndicator() {
        mRecipeListinatorFragment.hideLoadingIndicator();
    }

    @Override
    public void showLoadingIndicator() {
        mRecipeListinatorFragment.showLoadingIndicator();
    }

    @Override
    public void showRecipeList(List<RecipeModel> recipeList) {
        mRecipeListinatorFragment.addRecipeList(recipeList);
    }

    @Override
    public void showErrorLoadingRecipeList(Throwable error) {
        Timber.e(error, "An error occurred while tried fetch the recipe list");
        mRecipeListinatorFragment.showErrorLoadingRecipeList();
    }
}
