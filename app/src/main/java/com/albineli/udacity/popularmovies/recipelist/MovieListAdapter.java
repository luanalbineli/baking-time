package com.albineli.udacity.popularmovies.recipelist;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.albineli.udacity.popularmovies.util.UIUtil;

class MovieListAdapter extends CustomRecyclerViewAdapter<RecipeModel, RecipeItemViewHolder> {
    private String mPosterWidth;

    protected MovieListAdapter(int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        super(emptyMessageResId, tryAgainClickListener);
    }

    @Override
    protected RecipeItemViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new RecipeItemViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(RecipeItemViewHolder movieListViewHolder, int position) {
        if (mPosterWidth == null) {
            DisplayMetrics metrics = UIUtil.getDisplayMetrics(movieListViewHolder.getContext());
            int posterWidthPx = metrics.widthPixels / RecipeListFragment.getItensPerRow(movieListViewHolder.getContext());

            mPosterWidth = ApiUtil.getDefaultPosterSize(posterWidthPx);
        }

        final RecipeModel movieModel = getItemByPosition(position);

        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), mPosterWidth);
        movieListViewHolder.mMoviePosterSimpleDraweeView.setImageURI(posterUrl);
    }
}