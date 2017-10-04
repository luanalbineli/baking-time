package com.udacity.bakingtime.recipedetail.ingredientlistdialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;

import java.security.InvalidParameterException;

import javax.inject.Inject;


/*public class IngredientListDialog extends DialogFragment {
    @Inject
    MovieReviewListDialogPresenter mPresenter;

    public static MovieReviewListDialog getInstance(List<MovieReviewModel> movieModelList, int movieId, boolean hasMore) {
        MovieReviewListDialog instance = MovieReviewListDialog.createNewInstance(MovieReviewListDialog.class, movieModelList);
        if (instance != null && instance.getArguments() != null) {
            instance.getArguments().putBoolean(HAS_MORE_BUNDLE_KEY, hasMore);
            instance.getArguments().putInt(MOVIE_ID_BUNDLE_KEY, movieId);
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(HAS_MORE_BUNDLE_KEY) || !getArguments().containsKey(MOVIE_ID_BUNDLE_KEY)) {
            throw new InvalidParameterException("movie");
        }


        mHasMore = getArguments().getBoolean(HAS_MORE_BUNDLE_KEY);

        mMovieId = getArguments().getInt(MOVIE_ID_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fullScrennDialogView = super.onCreateView(inflater, container, savedInstanceState);

        mMovieReviewAdapter = new MovieReviewAdapter(() -> {
            mPresenter.tryLoadReviewsAgain();
        });

        mLinearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMovieReviewAdapter);

        return fullScrennDialogView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mList, mMovieId, mHasMore);
    }

    @Override
    public void addReviewsToList(List<MovieReviewModel> movieReviewList) {
        mMovieReviewAdapter.addItems(movieReviewList);
    }

    @Override
    public void enableLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) { // Check if the user scrolled down.
                    return;
                }
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount <= lastVisibleItem) {
                    *//*java.lang.IllegalStateException: Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data.
                    Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame. *//*
                    mRecyclerView.post(() -> mPresenter.onListEndReached());
                }
            }
        });
    }

    @Override
    public void disableLoadMoreListener() {
        mRecyclerView.clearOnScrollListeners();
    }

    @Override
    public void showLoadingIndicator() {
        mMovieReviewAdapter.showLoading();
    }

    @Override
    public void showErrorLoadingReviews() {
        mMovieReviewAdapter.showErrorMessage();
    }

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(PopularMovieApplication.getApplicationComponent(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    protected BasePresenter<MovieReviewListDialogContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected MovieReviewListDialogContract.View getViewImplementation() {
        return this;
    }
}*/

