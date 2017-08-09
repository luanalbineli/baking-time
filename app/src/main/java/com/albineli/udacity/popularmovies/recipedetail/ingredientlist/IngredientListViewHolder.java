package com.albineli.udacity.popularmovies.recipedetail.ingredientlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;

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
