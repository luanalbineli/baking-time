package com.udacity.bakingtime.recipelistinator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewHolder;

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
