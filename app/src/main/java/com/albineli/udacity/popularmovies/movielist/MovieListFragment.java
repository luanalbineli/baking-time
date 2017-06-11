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
import com.albineli.udacity.popularmovies.event.FavoriteMovieEvent;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailFragment;
import com.albineli.udacity.popularmovies.ui.recyclerview.CustomRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListFragment extends BaseFragment<MovieListContract.View> implements MovieListContract.View {
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

    GridLayoutManager mGridLayoutManager;

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
        mMovieListAdapter = new MovieListAdapter(R.string.the_list_is_empty, () -> mPresenter.tryAgain());
        mMovieListAdapter.setOnItemClickListener((position, movieModel) -> mPresenter.openMovieDetail(position, movieModel));

        mGridLayoutManager = new GridLayoutManager(mMovieListRecyclerView.getContext(), getItensPerRow(mMovieListRecyclerView.getContext()));
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mMovieListAdapter.getItemViewType(position)) {
                    case CustomRecyclerViewAdapter.ViewType.ITEM:
                        return 1;
                    default: // Grid status.
                        return mGridLayoutManager.getSpanCount();
                }
            }
        });
        mMovieListRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start(mFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
        mPresenter.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void onFavoriteMovieEvent(FavoriteMovieEvent favoriteMovieEvent) {
        mPresenter.favoriteMovie(favoriteMovieEvent.movie, favoriteMovieEvent.favorite);
    }

    @Override
    public void showLoadingMovieListError() {
        mMovieListAdapter.showErrorMessage();
    }

    @Override
    public void showMovieList(List<MovieModel> movieList, boolean replaceData) {
        if (replaceData) {
            mMovieListAdapter.replaceItems(movieList);
        } else {
            mMovieListAdapter.addItems(movieList);
        }
    }

    @Override
    public void showMovieDetail(MovieModel movieModel) {
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.getInstance(movieModel);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(R.id.fl_main_content, movieDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearMovieList() {
        mMovieListAdapter.clearItems();
    }

    @Override
    public void showEmptyListMessage() {
        mMovieListAdapter.showEmptyMessage();
    }

    @Override
    public void hideRequestStatus() {
        mMovieListAdapter.hideRequestStatus();
    }

    @Override
    public void showLoadingIndicator() {
        mMovieListAdapter.showLoading();
    }

    @Override
    public void enableLoadMoreListener() {
        // https://codentrick.com/load-more-recyclerview-bottom-progressbar
        disableLoadMoreListener();
        mMovieListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) { // Check if the user scrolled down.
                    return;
                }
                int totalItemCount = mGridLayoutManager.getItemCount();
                int lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + mGridLayoutManager.getSpanCount())) {
                    /*java.lang.IllegalStateException: Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data.
                    Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame. */
                    mMovieListRecyclerView.post(() -> mPresenter.onListEndReached());
                }
            }
        });
    }

    @Override
    public void disableLoadMoreListener() {
        mMovieListRecyclerView.clearOnScrollListeners();
    }

    @Override
    public void removeMovieFromListByIndex(int index) {
        mMovieListAdapter.removeItemByIndex(index);
    }

    @Override
    public void addMovieToListByIndex(int index, MovieModel movieModel) {
        mMovieListAdapter.insertItemByIndex(movieModel, index);
    }

    @Override
    public int getMovieListCount() {
        return mMovieListAdapter.getItemCount();
    }

    public void reloadListWithNewSort(@MovieListFilterDescriptor.MovieListFilter int movieListFilter) {
        mFilter = movieListFilter;
        mPresenter.setFilter(movieListFilter);

        if (getFragmentManager().getBackStackEntryCount() > 0) { // Are at detail screen
            getFragmentManager().popBackStack();
        }
    }

    public static int getItensPerRow(Context context) {
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            return isPortrait ? 5 : 6;
        }
        return isPortrait ? 3 : 4;
    }
}
