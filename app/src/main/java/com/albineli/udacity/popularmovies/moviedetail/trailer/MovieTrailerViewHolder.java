package com.albineli.udacity.popularmovies.moviedetail.trailer;

import android.view.View;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;
import com.borjabravo.readmoretextview.ReadMoreTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailerViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.tvMovieReviewAuthor)
    TextView mAuthorTextView;

    @BindView(R.id.tvMovieReviewContent)
    ReadMoreTextView mContentTextView;

    MovieTrailerViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
