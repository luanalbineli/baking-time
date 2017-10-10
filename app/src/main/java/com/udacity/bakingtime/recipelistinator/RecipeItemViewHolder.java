package com.udacity.bakingtime.recipelistinator;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeItemViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.ivRecipeItemPicture)
    ImageView mRecipeImageView;

    @BindView(R.id.tvRecipeItemName)
    TextView mRecipeNameTextView;

    @BindView(R.id.tvRecipeItemServing)
    TextView mRecipeServingTextView;

    RecipeItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    void bind(RecipeModel recipeModel) {
        mRecipeNameTextView.setText(recipeModel.getName());

        Resources resources = getContext().getResources();
        String servingText = resources.getQuantityString(R.plurals.number_of_serving,
                recipeModel.getServings(), // Determine if is plural by the quantity.
                recipeModel.getServings()); // Format.

        mRecipeServingTextView.setText(servingText);

        if (!TextUtils.isEmpty(recipeModel.getImage())) {
            Picasso.with(getContext()).load(recipeModel.getImage()).into(mRecipeImageView);
        }
    }
}
