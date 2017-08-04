package com.albineli.udacity.popularmovies.recipelist;

import android.view.View;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeItemViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvRecipeItemName)
    TextView mRecipeNameTextView;

    RecipeItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
