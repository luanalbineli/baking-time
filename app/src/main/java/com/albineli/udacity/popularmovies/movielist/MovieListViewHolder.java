package com.albineli.udacity.popularmovies.movielist;

import android.view.View;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieListViewHolder extends CustomRecyclerViewHolder {
    @BindView(R.id.sdvMovieItemPoster)
    SimpleDraweeView mMoviePosterSimpleDraweeView;

    MovieListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
