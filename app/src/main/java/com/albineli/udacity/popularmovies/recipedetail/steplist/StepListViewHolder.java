package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.borjabravo.readmoretextview.ReadMoreTextView;

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
        if (TextUtils.isEmpty(recipeStepModel.getRealVideoUrl())) {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.INVISIBLE);
        } else {
            stepListViewHolder.mRecipeStepVideoButton.setVisibility(View.VISIBLE);
        }
    }
}
