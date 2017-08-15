package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

class StepListAdapter extends CustomRecyclerViewAdapter<RecipeStepModel, StepListViewHolder> {
    private final OnClickStepVideoListener mOnClickStepVideoListener;

    protected StepListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener, OnClickStepVideoListener onClickStepVideoListener) {
        super(emptyMessageResId, tryAgainClickListener);

        mOnClickStepVideoListener = onClickStepVideoListener;
    }

    @Override
    protected StepListViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
        return new StepListViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(StepListViewHolder stepListViewHolder, int position) {
        final RecipeStepModel recipeStepModel = getItemByPosition(position);

        stepListViewHolder.mRecipeIngredientShortDescriptionTextView.setText(recipeStepModel.getShortDescription());
        stepListViewHolder.mRecipeIngredientLongDescriptionTextView.setText(recipeStepModel.getDescripton());
        if (TextUtils.isEmpty(recipeStepModel.getRealVideoUrl())) {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.INVISIBLE);
        } else {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.VISIBLE);
            stepListViewHolder.mRecipeStepVideoButton.setOnClickListener((view) -> mOnClickStepVideoListener.click(recipeStepModel));
        }
    }

    interface OnClickStepVideoListener {
        void click(RecipeStepModel recipeStepModel);
    }
}