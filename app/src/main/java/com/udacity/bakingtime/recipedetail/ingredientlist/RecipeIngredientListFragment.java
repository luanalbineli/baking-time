package com.udacity.bakingtime.recipedetail.ingredientlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.base.BaseRecyclerViewFragment;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeIngredientModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RecipeIngredientListFragment extends BaseRecyclerViewFragment<RecipeIngredientListContract.View> implements RecipeIngredientListContract.View {
    public static RecipeIngredientListFragment getInstance(List<RecipeIngredientModel> recipeIngredientList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPE_INGREDIENT_LIST_BUNDLE_KEY, new ArrayList<>(recipeIngredientList));

        RecipeIngredientListFragment movieDetailFragment = new RecipeIngredientListFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    protected BasePresenter<RecipeIngredientListContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeIngredientListContract.View getViewImplementation() {
        return this;
    }

    private static String RECIPE_INGREDIENT_LIST_BUNDLE_KEY = "recipe_ingredient_list";

    @Inject
    RecipeIngredientListPresenter mPresenter;

    private List<RecipeIngredientModel> mRecipeIngredientList;

    private IngredientListAdapter mAdapter;

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
        if (getArguments() != null && getArguments().containsKey(RECIPE_INGREDIENT_LIST_BUNDLE_KEY)) {
            mRecipeIngredientList = getArguments().getParcelableArrayList(RECIPE_INGREDIENT_LIST_BUNDLE_KEY);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mRecipeIngredientList);
    }

    public void addIngredientList(List<RecipeIngredientModel> ingredientListModel) {
        mPresenter.start(ingredientListModel);
    }

    @Override
    protected void configureRecyclerView(RecyclerView recyclerView) {
        mAdapter = new IngredientListAdapter(R.string.empty_ingredient_list, () -> mPresenter.start(mRecipeIngredientList));

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        useLinearLayoutManager();
        useDividerItemDecoration();
    }

    @Override
    public void showIngredientList(List<RecipeIngredientModel> recipeIngredientList) {
        mAdapter.addItems(recipeIngredientList);
    }
}
