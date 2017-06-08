package com.albineli.udacity.popularmovies.moviedetail.trailer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

public class MovieTrailerAdapter extends CustomRecyclerViewAdapter<MovieTrailerModel, MovieTrailerViewHolder> {
    @Override
    protected MovieTrailerViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_item, parent, false);
        return new MovieTrailerViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(MovieTrailerViewHolder movieListViewHolder, int position) {
        final MovieTrailerModel movieReviewModel = getItemByPosition(position);
        /*movieListViewHolder.mAuthorTextView.setText(movieReviewModel.getAuthor());
        movieListViewHolder.mContentTextView.setText(movieReviewModel.getContent());*/
    }
}