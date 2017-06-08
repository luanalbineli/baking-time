package com.albineli.udacity.popularmovies.moviedetail.trailer;

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
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieTrailerListDialog extends BaseFullscreenDialogWithList<MovieTrailerModel, MovieTrailerListDialogContract.View> implements MovieTrailerListDialogContract.View {
    private static final String MOVIE_TRAILER_LIST_BUNDLE_KEY = "movie_trailer_list";
    private ArrayList<MovieTrailerModel> mMovieTrailerList;

    MovieTrailerAdapter mMovieReviewAdapter;

    LinearLayoutManager mLinearLayoutManager;

    @Inject
    MovieTrailerListDialogPresenter mPresenter;

    public static MovieTrailerListDialog getInstance(List<MovieTrailerModel> movieModelList) {
        MovieTrailerListDialog instance = MovieTrailerListDialog.createNewInstance(MovieTrailerListDialog.class, movieModelList);

        return instance;
       /* Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_TRAILER_LIST_BUNDLE_KEY, new ArrayList<>(movieModelList));

        MovieTrailerListDialog filterDialogFragment = new MovieTrailerListDialog();
        filterDialogFragment.setArguments(bundle);
        filterDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_DialogFullscreen);
        return filterDialogFragment;*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(MOVIE_TRAILER_LIST_BUNDLE_KEY)) {
            throw new InvalidParameterException("movie");
        }

        mMovieTrailerList = getArguments().getParcelableArrayList(MOVIE_TRAILER_LIST_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fullscreenDialogWithListView = super.onCreateView(inflater, container, savedInstanceState);

        mMovieReviewAdapter = new MovieTrailerAdapter();

        mLinearLayoutManager = new LinearLayoutManager(mReviewRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mReviewRecyclerView.getContext(), mLinearLayoutManager.getOrientation());

        mReviewRecyclerView.addItemDecoration(dividerItemDecoration);
        mReviewRecyclerView.setLayoutManager(mLinearLayoutManager);
        mReviewRecyclerView.setAdapter(mMovieReviewAdapter);

        return fullscreenDialogWithListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mMovieTrailerList);
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
