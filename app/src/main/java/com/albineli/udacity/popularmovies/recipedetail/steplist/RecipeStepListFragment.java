package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.base.BaseRecyclerViewFragment;
import com.albineli.udacity.popularmovies.event.SelectStepEvent;
import com.albineli.udacity.popularmovies.exoplayer.ExoPlayerFragment;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RecipeStepListFragment extends BaseRecyclerViewFragment<RecipeStepListContract.View> implements RecipeStepListContract.View {
    private boolean mUseMasterDetail;

    public static RecipeStepListFragment getInstance(List<RecipeStepModel> recipeStepList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPE_STEP_LIST_BUNDLE_KEY, new ArrayList<>(recipeStepList));

        RecipeStepListFragment stepListFragment = new RecipeStepListFragment();
        stepListFragment.setArguments(bundle);
        return stepListFragment;
    }

    @Override
    protected BasePresenter<RecipeStepListContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeStepListContract.View getViewImplementation() {
        return this;
    }

    private static String RECIPE_STEP_LIST_BUNDLE_KEY = "step_list";

    @Inject
    RecipeStepListPresenter mPresenter;

    StepListAdapter mAdapter;

    private List<RecipeStepModel> mRecipeStepList;

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
        if (getArguments() != null && getArguments().containsKey(RECIPE_STEP_LIST_BUNDLE_KEY)) {
            mRecipeStepList = getArguments().getParcelableArrayList(RECIPE_STEP_LIST_BUNDLE_KEY);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUseMasterDetail = getActivity().getResources().getBoolean(R.bool.useMasterDetail);

        mPresenter.start(mRecipeStepList);
    }

    @Override
    protected void configureRecyclerView(RecyclerView recyclerView) {
        mAdapter = new StepListAdapter(R.string.empty_step_list,
                () -> mPresenter.start(mRecipeStepList));

        mAdapter.setOnItemClickListener((index, recipeStepModel) -> mPresenter.handleStepVideoClick(index, recipeStepModel, mUseMasterDetail));

        recyclerView.setAdapter(mAdapter);
        useLinearLayoutManager();
        useDividerItemDecoration();
    }

    @Override
    public void showStepList(List<RecipeStepModel> recipeStepList) {
        mAdapter.addItems(recipeStepList);
    }

    @Override
    public void openStepVideo(String videoUrl) {
        ExoPlayerFragment exoPlayerFragment = ExoPlayerFragment.getInstance(videoUrl);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main_content, exoPlayerFragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void viewStepDetail(RecipeStepModel recipeStepModel) {
        EventBus.getDefault().post(new SelectStepEvent(recipeStepModel));
    }

    @Override
    public void setSelectedRecipeStep(int selectedRecipeStepIndex) {
        mAdapter.setSelectedStep(selectedRecipeStepIndex);
    }

    @Override
    public void clearSelectedStep() {
        mAdapter.clearSelectedStep();
    }

    public void setStepList(List<RecipeStepModel> recipeStepList) {
        mPresenter.handleRecipeStepList(recipeStepList);
    }
}
