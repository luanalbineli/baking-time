package com.albineli.udacity.popularmovies.moviedetail.review;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewListDialog extends DialogFragment implements MovieReviewListDialogContract.View {
    private static final String MOVIE_REVIEW_LIST_BUNDLE_KEY = "movie_review_list";
    private static final String HAS_MORE_BUNDLE_KEY = "movie_review_has_more";
    private static final String MOVIE_ID_BUNDLE_KEY = "movie_id";
    private ArrayList<MovieReviewModel> mReviewList;

    @BindView(R.id.rvMovieReviewsDialog)
    RecyclerView mReviewRecyclerView;

    @BindView(R.id.toolbarMovieReviewDialog)
    Toolbar mDialogToolbar;

    MovieReviewAdapter mMovieReviewAdapter;

    LinearLayoutManager mLinearLayoutManager;

    private boolean mHasMore;

    private int mMovieId;

    @Inject
    MovieReviewListDialogPresenter mPresenter;

    public static MovieReviewListDialog getInstance(List<MovieReviewModel> movieModelList, int movieId, boolean hasMore) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_REVIEW_LIST_BUNDLE_KEY, new ArrayList<>(movieModelList));
        bundle.putBoolean(HAS_MORE_BUNDLE_KEY, hasMore);
        bundle.putInt(MOVIE_ID_BUNDLE_KEY, movieId);

        MovieReviewListDialog filterDialogFragment = new MovieReviewListDialog();
        filterDialogFragment.setArguments(bundle);
        filterDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_DialogFullscreen);
        return filterDialogFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(MOVIE_REVIEW_LIST_BUNDLE_KEY) || !getArguments().containsKey(HAS_MORE_BUNDLE_KEY)) {
            throw new InvalidParameterException("movie");
        }

        mReviewList = getArguments().getParcelableArrayList(MOVIE_REVIEW_LIST_BUNDLE_KEY);

        mHasMore = getArguments().getBoolean(HAS_MORE_BUNDLE_KEY);

        mMovieId = getArguments().getInt(MOVIE_ID_BUNDLE_KEY);

        DaggerFragmentComponent.builder()
                .applicationComponent(PopularMovieApplication.getApplicationComponent(getActivity()))
                .build()
                .inject(this);

        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_review_list_dialog, container);

        ButterKnife.bind(this, rootView);

        mMovieReviewAdapter = new MovieReviewAdapter();

        mLinearLayoutManager = new LinearLayoutManager(mReviewRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mReviewRecyclerView.getContext(), mLinearLayoutManager.getOrientation());

        mReviewRecyclerView.addItemDecoration(dividerItemDecoration);
        mReviewRecyclerView.setLayoutManager(mLinearLayoutManager);
        mReviewRecyclerView.setAdapter(mMovieReviewAdapter);

        mDialogToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
        mDialogToolbar.setNavigationOnClickListener(v -> dismiss());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mReviewList, mMovieId, mHasMore);
    }

    @Override
    public void addReviewsToList(List<MovieReviewModel> movieReviewList) {
        mMovieReviewAdapter.addItems(movieReviewList);
    }

    @Override
    public void enableLoadMoreListener() {
        mReviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) { // Check if the user scrolled down.
                    return;
                }
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount <= lastVisibleItem) {
                    /*java.lang.IllegalStateException: Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data.
                    Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame. */
                    mReviewRecyclerView.post(() -> mPresenter.onListEndReached());
                }
            }
        });
    }

    @Override
    public void disableLoadMoreListener() {
        mReviewRecyclerView.clearOnScrollListeners();
    }

    @Override
    public void showLoadingIndicator() {
        mMovieReviewAdapter.showLoading();
    }

    @Override
    public void showErrorLoadingReviews() {
        mMovieReviewAdapter.showErrorMessage();
    }
}
