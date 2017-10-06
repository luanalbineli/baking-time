package com.udacity.bakingtime.recipelistinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.ui.RequestStatusView;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewAdapter;

class RecipeListinatorAdapter extends CustomRecyclerViewAdapter<RecipeModel, RecipeItemViewHolder> {
    RecipeListinatorAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
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
        recipeItemViewHolder.bind(recipeModel);
    }
}