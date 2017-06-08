package com.albineli.udacity.popularmovies.movielist;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.albineli.udacity.popularmovies.util.UIUtil;

class MovieListAdapter extends CustomRecyclerViewAdapter<MovieModel, MovieListViewHolder> {
    private String mPosterWidth;

    @Override
    protected MovieListViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieListViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(MovieListViewHolder movieListViewHolder, int position) {
        if (mPosterWidth == null) {
            DisplayMetrics metrics = UIUtil.getDisplayMetrics(movieListViewHolder.getContext());
            int posterWidthPx = metrics.widthPixels / MovieListFragment.getItensPerRow(movieListViewHolder.getContext());

            mPosterWidth = ApiUtil.getDefaultPosterSize(posterWidthPx);
        }

        final MovieModel movieModel = getItemByPosition(position);

        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), mPosterWidth);
        movieListViewHolder.mMoviePosterSimpleDraweeView.setImageURI(posterUrl);
    }
}