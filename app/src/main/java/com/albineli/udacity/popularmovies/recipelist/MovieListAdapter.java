package com.albineli.udacity.popularmovies.recipelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

class MovieListAdapter extends CustomRecyclerViewAdapter<RecipeModel, RecipeItemViewHolder> {
    protected MovieListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        super(emptyMessageResId, tryAgainClickListener);
    }

    @Override
    protected RecipeItemViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeItemViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(RecipeItemViewHolder movieListViewHolder, int position) {
        RecipeModel recipeModel = getItemByPosition(position);
        movieListViewHolder.mRecipeNameTextView.setText(recipeModel.getName());
    }
}