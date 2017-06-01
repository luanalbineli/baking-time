package com.albineli.udacity.popularmovies.moviedetail.review;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailReviewViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvMovieReviewAuthor)
    TextView mAuthorTextView;

    @BindView(R.id.tvMovieReviewContent)
    TextView mContentTextView;

    MovieDetailReviewViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
