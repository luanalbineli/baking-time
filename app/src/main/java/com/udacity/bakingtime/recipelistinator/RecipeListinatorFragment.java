package com.udacity.bakingtime.recipelistinator;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.base.BaseRecyclerViewFragment;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.ui.RequestStatusView;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class RecipeListinatorFragment extends BaseRecyclerViewFragment<RecipeListinatorContract.View> implements RecipeListinatorContract.View {
    private RequestStatusView.ITryAgainClickListener mTryAgainListener;

    @Override
    protected BasePresenter<RecipeListinatorContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeListinatorContract.View getViewImplementation() {
        return this;
    }

    @Inject
    RecipeListinatorPresenter mPresenter;

    RecipeListinatorAdapter mRecipeListAdapter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configureRecyclerView(RecyclerView recyclerView) {
        mRecipeListAdapter = new RecipeListinatorAdapter(R.string.the_list_is_empty, () -> {
            if (mTryAgainListener != null) {
                mTryAgainListener.tryAgain();
            }
        });

        mRecipeListAdapter.setOnItemClickListener((position, recipeModel) -> mPresenter.handleRecipeItemClick(position, recipeModel));

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
    public void onClickRecipeItem(RecipeModel recipeModel) {
        EventBus.getDefault().post(new SelectRecipeEvent(recipeModel));
    }

    public void hideLoadingIndicator() {
        mRecipeListAdapter.hideRequestStatus();
    }

    public void showLoadingIndicator() {
        mRecipeListAdapter.showLoading();
    }

    public void setTryAgainClickListener(RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        this.mTryAgainListener = tryAgainClickListener;
    }

    public void addRecipeList(List<RecipeModel> recipeList) {
        mPresenter.showRecipeList(recipeList);
    }

    @Override
    public void showRecipeList(List<RecipeModel> recipeList) {
        mRecipeListAdapter.addItems(recipeList);
    }

    public void showErrorLoadingRecipeList() {
        mRecipeListAdapter.showErrorMessage();
    }
}
