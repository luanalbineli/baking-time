package com.albineli.udacity.popularmovies.movielist;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.enums.RequestStatusDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.BaseViewHolder> {
    static final int ITEM_TYPE_ITEM = 0;
    private static final int ITEM_TYPE_GRID_STATUS = 1;

    private static String mPosterWidth;
    private final OnMovieClickListener mOnMovieClickListener;
    private List<MovieModel> mMovieList;

    private @RequestStatusDescriptor.RequestStatus
    int mGridStatus = RequestStatusDescriptor.LOADING;

    private @StringRes
    int mEmptyMessageResId;

    MovieListAdapter(@NonNull List<MovieModel> movieList, OnMovieClickListener onMovieClickListener) {
        mMovieList = movieList;
        mOnMovieClickListener = onMovieClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_GRID_STATUS) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_status, parent, false);
            return new GridStatusViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, int position) {
        if (baseViewHolder.getItemViewType() == ITEM_TYPE_GRID_STATUS) {
            bindGridStatus((GridStatusViewHolder) baseViewHolder);
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) baseViewHolder;

        if (mPosterWidth == null) { // TODO: Hardcoded
            mPosterWidth = ApiUtil.getDefaultPosterSize(itemViewHolder.mMoviePosterImageView.getWidth());
        }

        final MovieModel movieModel = mMovieList.get(position);

        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), mPosterWidth);
        Picasso.with(baseViewHolder.itemView.getContext())
                .load(posterUrl)
                .into(itemViewHolder.mMoviePosterImageView);

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMovieClickListener.onClick(baseViewHolder.getAdapterPosition(), movieModel);
            }
        });
    }

    private void bindGridStatus(GridStatusViewHolder gridStatusViewHolder) {
        gridStatusViewHolder.mRequestStatusView.setRequestStatus(mGridStatus, mMovieList.size() == 0 && mGridStatus != RequestStatusDescriptor.HIDDEN);
        if (mGridStatus == RequestStatusDescriptor.EMPTY) {
            gridStatusViewHolder.mRequestStatusView.setEmptyMessage(mEmptyMessageResId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mMovieList.size()) {
            return ITEM_TYPE_GRID_STATUS;
        }
        return ITEM_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        // It will show the movie list and the one more item for the grid status.
        return mMovieList.size() + 1;
    }

    void replaceData(List<MovieModel> movieList) {
        clearData();
        mMovieList.addAll(movieList);
        notifyItemRangeChanged(0, movieList.size());
    }

    void addData(List<MovieModel> movieList) {
        int startIndexInserted = mMovieList.size();

        mMovieList.addAll(movieList);
        notifyItemRangeInserted(startIndexInserted, movieList.size());
    }

    void showErrorLoadingContent() {
        redrawGridStatus(RequestStatusDescriptor.ERROR);
    }

    void showEmptyMessage(@StringRes int emptyMessageResId) {
        mEmptyMessageResId = emptyMessageResId;
        redrawGridStatus(RequestStatusDescriptor.EMPTY);
    }

    void hideStatus() {
        redrawGridStatus(RequestStatusDescriptor.HIDDEN);
    }

    private void redrawGridStatus(@RequestStatusDescriptor.RequestStatus int status) {
        mGridStatus = status;
        notifyItemChanged(mMovieList.size());
    }

    void clearData() {
        if (mMovieList.size() > 0) {
            notifyItemRangeRemoved(0, mMovieList.size());
        }
        mMovieList.clear();
    }

    interface OnMovieClickListener {
        void onClick(int index, MovieModel movieModel);
    }

    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_movie_item_poster)
        ImageView mMoviePosterImageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class GridStatusViewHolder extends BaseViewHolder {
        @BindView(R.id.rsvRequestStatus)
        RequestStatusView mRequestStatusView;

        GridStatusViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
