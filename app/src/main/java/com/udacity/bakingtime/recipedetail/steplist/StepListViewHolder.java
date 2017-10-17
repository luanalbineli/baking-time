package com.udacity.bakingtime.recipedetail.steplist;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeStepModel;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepListViewHolder extends StepListShortTitleViewHolder {
    @BindView(R.id.ibRecipeStepVideo)
    FloatingActionButton mRecipeStepVideoButton;

    @BindView(R.id.tvRecipeStepLongDescription)
    ReadMoreTextView mRecipeIngredientLongDescriptionTextView;

    StepListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    void bindValues(StepListShortTitleViewHolder stepListShortTitleViewHolder, RecipeStepModel recipeStepModel, boolean isSelected) {
        stepListShortTitleViewHolder.mRecipeIngredientShortDescriptionTextView.setText(recipeStepModel.getShortDescription());

        StepListViewHolder stepListViewHolder = (StepListViewHolder) stepListShortTitleViewHolder;
        stepListViewHolder.mRecipeIngredientLongDescriptionTextView.setText(recipeStepModel.getDescripton());
        if (TextUtils.isEmpty(recipeStepModel.getVideoURL())) {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.INVISIBLE);
        } else {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.VISIBLE);
        }
    }
}
