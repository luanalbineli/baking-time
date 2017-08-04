package com.albineli.udacity.popularmovies.recipedetail.trailer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.base.BaseFullscreenDialogWithList;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.util.YouTubeUtil;

import java.util.List;

import javax.inject.Inject;

public class MovieTrailerListDialog extends BaseFullscreenDialogWithList<MovieTrailerModel, MovieTrailerListDialogContract.View> implements MovieTrailerListDialogContract.View {
    MovieTrailerAdapter mMovieReviewAdapter;

    LinearLayoutManager mLinearLayoutManager;

    @Inject
    MovieTrailerListDialogPresenter mPresenter;

    public static MovieTrailerListDialog getInstance(List<MovieTrailerModel> movieModelList) {
        return MovieTrailerListDialog.createNewInstance(MovieTrailerListDialog.class, movieModelList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fullscreenDialogWithListView = super.onCreateView(inflater, container, savedInstanceState);

        // The trailers endpoint is not paginated, so I don't need to implement the "Try again" constructor.
        mMovieReviewAdapter = new MovieTrailerAdapter();
        mMovieReviewAdapter.setOnItemClickListener((position, item) -> YouTubeUtil.openYouTubeVideo(getActivity(), item.getKey()));

        mLinearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMovieReviewAdapter);

        return fullscreenDialogWithListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mList);
    }

    @Override
    public void showTrailersIntoList(List<MovieTrailerModel> movieReviewList) {
        mMovieReviewAdapter.addItems(movieReviewList);
    }

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(PopularMovieApplication.getApplicationComponent(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    protected BasePresenter<MovieTrailerListDialogContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected MovieTrailerListDialogContract.View getViewImplementation() {
        return this;
    }
}
