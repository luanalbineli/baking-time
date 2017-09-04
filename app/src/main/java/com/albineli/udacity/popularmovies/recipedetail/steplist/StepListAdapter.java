package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

class StepListAdapter extends CustomRecyclerViewAdapter<RecipeStepModel, StepListShortTitleViewHolder> {
    private int mSelectedRecipeStepIndex = -1;

    protected StepListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        super(emptyMessageResId, tryAgainClickListener);
    }

    @Override
    protected StepListShortTitleViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
        if (parent.getContext().getResources().getBoolean(R.bool.useMasterDetail)) {
            return new StepListShortTitleViewHolder(itemView);
        }
        return new StepListViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(StepListShortTitleViewHolder stepListShortTitleViewHolder, int position) {
        final RecipeStepModel recipeStepModel = getItemByPosition(position);

        stepListShortTitleViewHolder.bindValues(stepListShortTitleViewHolder, recipeStepModel, mSelectedRecipeStepIndex == position);
    }

    void setSelectedStep(int stepIndex) {
        mSelectedRecipeStepIndex = stepIndex;
        notifyItemChanged(stepIndex);
    }

    void clearSelectedStep() {
        int selectedRecipeStepIndexTemp = mSelectedRecipeStepIndex;
        mSelectedRecipeStepIndex = -1;
        notifyItemChanged(selectedRecipeStepIndexTemp);
    }
}