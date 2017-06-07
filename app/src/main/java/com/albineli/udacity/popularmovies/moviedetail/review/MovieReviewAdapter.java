package com.albineli.udacity.popularmovies.moviedetail.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

public class MovieReviewAdapter extends CustomRecyclerViewAdapter<MovieReviewModel, MovieDetailReviewViewHolder> {
    @Override
    protected MovieDetailReviewViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_item, parent, false);
        return new MovieDetailReviewViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(MovieDetailReviewViewHolder movieListViewHolder, int position) {
        final MovieReviewModel movieReviewModel = getItemByPosition(position);
        movieListViewHolder.mAuthorTextView.setText(movieReviewModel.getAuthor());
        movieListViewHolder.mContentTextView.setText(movieReviewModel.getContent());
    }
}