package com.udacity.bakingtime.recipedetail.steplist;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;


import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeStepModel;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewHolder;

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
