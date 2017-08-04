package com.albineli.udacity.popularmovies.recipelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class RecipeListFragment extends BaseFragment<RecipeListContract.View> implements RecipeListContract.View {
    public static RecipeListFragment getInstance() {
        return new RecipeListFragment();
    }

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

    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecipeRecyclerView;

    MovieListAdapter mRecipeListAdapter;

    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        // List.
        mRecipeListAdapter = new MovieListAdapter(R.string.the_list_is_empty, () -> mPresenter.start());
        mRecipeListAdapter.setOnItemClickListener((position, recipeModel) -> mPresenter.openRecipeDetail(position, recipeModel));

        mLinearLayoutManager = new LinearLayoutManager(mRecipeRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecipeRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeListAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void hideLoadingIndicator() {
        mRecipeListAdapter.hideRequestStatus();
    }

    @Override
    public void showLoadingIndicator() {
        mRecipeListAdapter.showLoading();
    }

    @Override
    public void showRecipeList(List<RecipeModel> recipeList) {
        mRecipeListAdapter.addItems(recipeList);
    }

    @Override
    public void showErrorLoadingRecipeList(Throwable error) {
        Timber.e(error, "An error occurred while tried fetch the recipe list");
        mRecipeListAdapter.showErrorMessage();
    }
}
