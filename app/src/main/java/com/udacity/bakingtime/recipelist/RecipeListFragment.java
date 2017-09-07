package com.udacity.bakingtime.recipelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseFragment;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.RecipeDetailFragment;
import com.udacity.bakingtime.recipelistinator.RecipeListinatorFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;


public class RecipeListFragment extends BaseFragment<RecipeListContract.View> implements RecipeListContract.View {
    public static RecipeListFragment getInstance() {
        return new RecipeListFragment();
    }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(R.string.recipe_list);

        mRecipeListinatorFragment = (RecipeListinatorFragment) getChildFragmentManager().findFragmentById(R.id.fragmentRecipeListinator);

        mPresenter.start();
    }

    @Override
    public void goToRecipeDetail(RecipeModel recipeModel) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(recipeModel);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_main_content, recipeDetailFragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
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
