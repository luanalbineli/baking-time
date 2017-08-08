package com.albineli.udacity.popularmovies.recipelist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeItemViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.ivRecipeItemPicture)
    ImageView mRecipeImageDraweeView;

    @BindView(R.id.tvRecipeItemName)
    TextView mRecipeNameTextView;

    @BindView(R.id.tvRecipeItemServing)
    TextView mRecipeServingTextView;

    RecipeItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
