package com.albineli.udacity.popularmovies.movielist;

import android.view.View;
import android.widget.ImageView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieListViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.iv_movie_item_poster)
    ImageView mMoviePosterImageView;

    MovieListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
