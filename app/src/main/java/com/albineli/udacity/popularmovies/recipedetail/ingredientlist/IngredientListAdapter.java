package com.albineli.udacity.popularmovies.recipedetail.ingredientlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeIngredientModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class IngredientListAdapter extends CustomRecyclerViewAdapter<RecipeIngredientModel, IngredientListViewHolder> {
    private static final NumberFormat QUANTITY_FORMAT = new DecimalFormat("##.###");

    IngredientListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        super(emptyMessageResId, tryAgainClickListener);
    }

    @Override
    protected IngredientListViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredient_item, parent, false);
        return new IngredientListViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(IngredientListViewHolder recipeItemViewHolder, int position) {
        RecipeIngredientModel recipeIngredientModel = getItemByPosition(position);

        String formattedQuantity = QUANTITY_FORMAT.format(recipeIngredientModel.getQuantity());
        recipeItemViewHolder.mRecipeIngredientQuantityTextView.setText(formattedQuantity);

        recipeItemViewHolder.mRecipeIngredientMeasurementUnitTextView.setText(recipeIngredientModel.getMeasure());

        recipeItemViewHolder.mRecipeIngredientNameTextView.setText(recipeIngredientModel.getIngredient());
    }
}