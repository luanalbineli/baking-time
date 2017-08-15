package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;
import com.borjabravo.readmoretextview.ReadMoreTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepListViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.ibRecipeStepVideo)
    FloatingActionButton mRecipeStepVideoButton;

    @BindView(R.id.tvRecipeStepShortDescription)
    TextView mRecipeIngredientShortDescriptionTextView;

    @BindView(R.id.tvRecipeStepLongDescription)
    ReadMoreTextView mRecipeIngredientLongDescriptionTextView;

    StepListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
