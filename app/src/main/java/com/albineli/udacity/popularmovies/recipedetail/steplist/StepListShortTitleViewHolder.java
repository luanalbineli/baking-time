package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepListShortTitleViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvRecipeStepShortDescription)
    TextView mRecipeIngredientShortDescriptionTextView;

    StepListShortTitleViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


    void bindValues(StepListShortTitleViewHolder stepListShortTitleViewHolder, RecipeStepModel recipeStepModel, boolean isSelected) {
        stepListShortTitleViewHolder.mRecipeIngredientShortDescriptionTextView.setText(recipeStepModel.getShortDescription());

        View selectedIndicatorView = ButterKnife.findById(stepListShortTitleViewHolder.itemView, R.id.tvRecipeStepSelectedIndicator);
        @ColorRes int selectedIndicatorBackground = R.color.colorPrimary;
        if (isSelected) {
            selectedIndicatorBackground = R.color.colorAccent;
        }

        ColorDrawable colorDrawable = new ColorDrawable(stepListShortTitleViewHolder.getContext().getResources().getColor(selectedIndicatorBackground));
        selectedIndicatorView.setBackground(colorDrawable);
    }
}
