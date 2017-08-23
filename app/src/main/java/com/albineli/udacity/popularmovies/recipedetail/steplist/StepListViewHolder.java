package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.albineli.udacity.popularmovies.R;
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
}
