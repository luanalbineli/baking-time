package com.albineli.udacity.popularmovies.movielist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailFragment;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListFragment extends BaseFragment<MovieListContract.View> implements MovieListContract.View, MovieListAdapter.OnMovieClickListener {
    private static final String FILTER_BUNDLE_KEY = "movie_list_filter_bundle";

    public static MovieListFragment getInstance(@MovieListFilterDescriptor.MovieListFilter int filter) {
        Bundle bundle = new Bundle();
        bundle.putInt(FILTER_BUNDLE_KEY, filter);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(bundle);

        return movieListFragment;
    }

    @Override
    protected BasePresenter<MovieListContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected MovieListContract.View getViewImplementation() {
        return this;
    }

    @Inject
    MovieListPresenter mPresenter;

    @BindView(R.id.rv_movie_list)
    RecyclerView mMovieListRecyclerView;

    MovieListAdapter mMovieListAdapter;

    @MovieListFilterDescriptor.MovieListFilter
    private int mFilter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getArguments() == null || !getArguments().containsKey(FILTER_BUNDLE_KEY)) {
            throw new InvalidParameterException("filter");
        }

        mFilter = MovieListFilterDescriptor.parseFromInt(getArguments().getInt(FILTER_BUNDLE_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        // List.
        mMovieListAdapter = new MovieListAdapter(new ArrayList<MovieModel>(0), this);

        final int itensPerRow = getItensPerRow();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mMovieListRecyclerView.getContext(), itensPerRow);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mMovieListAdapter.getItemViewType(position)) {
                    case MovieListAdapter.ITEM_TYPE_ITEM:
                        return 1;
                    default: // Grid status.
                        return itensPerRow;
                }
            }
        });
        mMovieListRecyclerView.setLayoutManager(gridLayoutManager);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);

        // https://codentrick.com/load-more-recyclerview-bottom-progressbar
        mMovieListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) { // Check if the user scrolled down.
                    return;
                }
                int totalItemCount = gridLayoutManager.getItemCount();
                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + itensPerRow)) {
                    mPresenter.onListEndReached();
                }
            }
        });

        return rootView;
    }

    private int getItensPerRow() {
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            return isPortrait ? 5 : 6;
        }
        return isPortrait ? 3 : 4;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start(mFilter);
    }
    @Override
    public void onStop() {
        super.onStop();

        mPresenter.onStop();
    }

    @Override
    public void showLoadingMovieListError() {
        mMovieListAdapter.showErrorLoadingContent();
    }

    @Override
    public void showMovieList(List<MovieModel> movieList, boolean replaceData) {
        if (replaceData) {
            mMovieListAdapter.replaceData(movieList);
        } else {
            mMovieListAdapter.addData(movieList);
        }
    }

    @Override
    public void showMovieDetail(MovieModel movieModel) {
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.getInstance(movieModel);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_main_content, movieDetailFragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearMovieList() {
        mMovieListAdapter.clearData();
    }

    @Override
    public void showEmptyListMessage() {
        mMovieListAdapter.showEmptyMessage();
    }

    @Override
    public void hideLoadingMovieListError() {
        mMovieListAdapter.hideStatus();
    }

    public void reloadListWithNewSort(@MovieListFilterDescriptor.MovieListFilter int movieListFilter) {
        mFilter = movieListFilter;
        mPresenter.setFilter(movieListFilter);
    }

    @Override
    public void onClick(int index, MovieModel movieModel) {
        mPresenter.openMovieDetail(movieModel);
    }
}
