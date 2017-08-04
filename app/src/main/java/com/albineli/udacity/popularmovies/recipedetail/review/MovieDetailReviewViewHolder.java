package com.albineli.udacity.popularmovies.recipedetail.review;

import android.view.View;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;
import com.borjabravo.readmoretextview.ReadMoreTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailReviewViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvMovieReviewAuthor)
    TextView mAuthorTextView;

    @BindView(R.id.tvMovieReviewContent)
    ReadMoreTextView mContentTextView;

    MovieDetailReviewViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
