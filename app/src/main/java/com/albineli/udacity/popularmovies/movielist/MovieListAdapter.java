package com.albineli.udacity.popularmovies.movielist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.albineli.udacity.popularmovies.util.LogUtils.makeLogTag;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.BaseViewHolder> {
    static final int ITEM_TYPE_ITEM = 0;
    static final int ITEM_TYPE_GRID_STATUS = 1;

    private static final String TAG = makeLogTag(MovieListAdapter.class);

    private static String mPosterWidth;
    private final OnMovieClickListener mOnMovieClickListener;
    private onTryAgainListener mOnTryAgainListener;
    private List<MovieModel> mMovieList;
    private int mGridStatus = GridStatus.LOADING;

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
            handleGridStatus((GridStatusViewHolder) baseViewHolder);
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

    private void handleGridStatus(GridStatusViewHolder gridStatusViewHolder) {
        if (mGridStatus == GridStatus.LOADING) {
            toggleGridStatus(gridStatusViewHolder, true);
        } else {
            toggleGridStatus(gridStatusViewHolder, false);
            gridStatusViewHolder.mTryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnTryAgainListener != null) {
                        mOnTryAgainListener.tryAgain();
                    }
                }
            });
        }

        // Handle the grid status size.
        int gridStatusHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        if (mMovieList.size() == 0) {
            gridStatusHeight = FrameLayout.LayoutParams.MATCH_PARENT;
        }
        ViewGroup.LayoutParams layoutParams = gridStatusViewHolder.itemView.getLayoutParams();
        layoutParams.height = gridStatusHeight;
        gridStatusViewHolder.itemView.setLayoutParams(layoutParams);
    }

    private void toggleGridStatus(GridStatusViewHolder gridStatusViewHolder, boolean loadingVisible) {
        gridStatusViewHolder.mLoadingProgressBar.setVisibility(loadingVisible ? View.VISIBLE : View.INVISIBLE);
        gridStatusViewHolder.mErrorContainerView.setVisibility(loadingVisible ? View.INVISIBLE : View.VISIBLE);
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
        if (mMovieList.size() > 0) {
            notifyItemRangeRemoved(0, mMovieList.size());
        }
        mMovieList.clear();
        mMovieList.addAll(movieList);
        notifyItemRangeChanged(0, movieList.size());

        redrawGridStatus(GridStatus.LOADING);
    }

    void addData(List<MovieModel> movieList) {
        int startIndexInserted = mMovieList.size();

        mMovieList.addAll(movieList);
        notifyItemRangeInserted(startIndexInserted, movieList.size());

        redrawGridStatus(GridStatus.LOADING);
    }

    void showErrorLoadingContent() {
        redrawGridStatus(GridStatus.ERROR);
    }

    private void redrawGridStatus(int status) {
        mGridStatus = status;
        notifyItemChanged(mMovieList.size());
    }

    public void setOnTryAgainListener(onTryAgainListener onTryAgainListener) {
        this.mOnTryAgainListener = onTryAgainListener;
    }

    void hideErrorLoadingContent() {
        redrawGridStatus(GridStatus.LOADING);
    }

    interface OnMovieClickListener {
        void onClick(int index, MovieModel movieModel);
    }

    interface onTryAgainListener {
        void tryAgain();
    }

    interface GridStatus {
        int LOADING = 1;
        int ERROR = 2;
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
        @BindView(R.id.ll_grid_status_error)
        LinearLayout mErrorContainerView;

        @BindView(R.id.bt_grid_status_retry)
        Button mTryAgainButton;

        @BindView(R.id.pb_grid_status_loading)
        ProgressBar mLoadingProgressBar;

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
