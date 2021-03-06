package com.udacity.bakingtime.recipelist;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.ui.RequestStatusView;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewAdapter;

class RecipeListAdapter extends CustomRecyclerViewAdapter<RecipeModel, RecipeItemViewHolder> {
    protected RecipeListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        super(emptyMessageResId, tryAgainClickListener);
    }

    @Override
    protected RecipeItemViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeItemViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(RecipeItemViewHolder recipeItemViewHolder, int position) {
        RecipeModel recipeModel = getItemByPosition(position);
        recipeItemViewHolder.mRecipeNameTextView.setText(recipeModel.getName());

        Resources resources = recipeItemViewHolder.getContext().getResources();
        String servingText = resources.getQuantityString(R.plurals.number_of_serving,
                recipeModel.getServings(), // Determine if is plural by the quantity.
                recipeModel.getServings()); // Format.

        recipeItemViewHolder.mRecipeServingTextView.setText(servingText);

    }
}