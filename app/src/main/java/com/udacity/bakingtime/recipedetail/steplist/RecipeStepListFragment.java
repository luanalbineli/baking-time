package com.udacity.bakingtime.recipedetail.steplist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.base.BaseRecyclerViewFragment;
import com.udacity.bakingtime.event.SelectStepEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeStepModel;
import com.udacity.bakingtime.recipedetail.stepvideo.StepVideoDialog;

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
    public void openStepVideo(String title, String videoUrl) {
        StepVideoDialog stepVideoDialog = StepVideoDialog.getInstance(title, videoUrl);
        stepVideoDialog.show(getChildFragmentManager(), "video_dialog");
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
