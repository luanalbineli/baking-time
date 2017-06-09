package com.albineli.udacity.popularmovies.moviedetail.trailer;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

import timber.log.Timber;

public class MovieTrailerAdapter extends CustomRecyclerViewAdapter<MovieTrailerModel, MovieTrailerViewHolder> {
    private static final String URL_YOUTUBE_THUMBNAIL_FORMAT = "https://img.youtube.com/vi/%1$s/0.jpg";

    @Override
    protected MovieTrailerViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_item, parent, false);
        return new MovieTrailerViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(MovieTrailerViewHolder movieTrailerViewHolder, int position) {
        final MovieTrailerModel movieTrailerModel = getItemByPosition(position);
        movieTrailerViewHolder.mNameTextView.setText(movieTrailerModel.getName());

        Timber.i("Bindind trailer: " + movieTrailerModel);

        movieTrailerViewHolder.mSizeTextView.setText(movieTrailerModel.getSize() + "p"); // TODO: String resources.

        if (movieTrailerModel.getSite().equalsIgnoreCase("YouTube")) { // TODO: I didn't found a non youtube video, but I must handle it
            movieTrailerViewHolder.mThumbnailSimpleDraweeView.setImageURI(String.format(URL_YOUTUBE_THUMBNAIL_FORMAT, movieTrailerModel.getKey()));
            movieTrailerViewHolder.mSourceTextView.setText(R.string.youtube);
            movieTrailerViewHolder.mSourceTextView.setVisibility(View.VISIBLE);
        } else {
            movieTrailerViewHolder.mSourceTextView.setVisibility(View.GONE);
        }
    }
}