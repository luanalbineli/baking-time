package com.udacity.bakingtime.recipedetail.ingredientlist;

import android.view.View;
import android.widget.TextView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientListViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvRecipeIngredientQuantity)
    TextView mRecipeIngredientQuantityTextView;

    @BindView(R.id.tvRecipeIngredientMeasurementUnit)
    TextView mRecipeIngredientMeasurementUnitTextView;

    @BindView(R.id.tvRecipeIngredientName)
    TextView mRecipeIngredientNameTextView;

    IngredientListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
