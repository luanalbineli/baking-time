package com.udacity.bakingtime.recipelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.base.BaseRecyclerViewFragment;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.RecipeDetailFragment;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;


public class RecipeListFragment extends BaseRecyclerViewFragment<RecipeListContract.View> implements RecipeListContract.View {
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

    @BindView(R.id.rvFragment)
    RecyclerView mRecipeRecyclerView;

    RecipeListAdapter mRecipeListAdapter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(R.string.recipe_list);

        mPresenter.start();
    }

    @Override
    protected void configureRecyclerView(RecyclerView recyclerView) {
        mRecipeListAdapter = new RecipeListAdapter(R.string.the_list_is_empty, () -> mPresenter.start());
        mRecipeListAdapter.setOnItemClickListener((position, recipeModel) -> mPresenter.openRecipeDetail(position, recipeModel));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRecipeListAdapter);

        if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
            configureTabletLayout();
        } else {
            useLinearLayoutManager();
        }
    }

    private void configureTabletLayout() {
        GridLayoutManager gridLayoutManager = useGridLayoutManager(getActivity().getResources().getInteger(R.integer.recipe_list_columns));

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mRecipeListAdapter.getItemViewType(position)) {
                    case CustomRecyclerViewAdapter.ViewType.ITEM:
                        return 1;
                    default: // Grid status.
                        return gridLayoutManager.getSpanCount();
                }
            }
        });
    }

    @Override
    public void goToRecipeDetail(RecipeModel recipeModel) {
     /*   RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(recipeModel);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_main_content, recipeDetailFragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();*/
        EventBus.getDefault().post(new SelectRecipeEvent(recipeModel));
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
