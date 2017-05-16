package com.albineli.udacity.popularmovies.movielist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerMovieListComponent;
import com.albineli.udacity.popularmovies.injector.modules.MovieListModule;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor.POPULAR;
import static com.albineli.udacity.popularmovies.util.LogUtils.makeLogTag;


public class MovieListFragment extends BaseFragment implements MovieListContract.View, MovieListAdapter.OnMovieClickListener, MovieListAdapter.onTryAgainListener {
    public static MovieListFragment getInstance() {
        return new MovieListFragment();
    }

    private static final String TAG = makeLogTag(MovieListFragment.class);

    /*
     Injects the presenter
     */
    @Inject
    MovieListContract.Presenter mPresenter;

    @BindView(R.id.rv_movie_list)
    RecyclerView mMovieListRecyclerView;

    MovieListAdapter mMovieListAdapter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerMovieListComponent.builder()
                .applicationComponent(applicationComponent)
                .movieListModule(new MovieListModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        // List.
        mMovieListAdapter = new MovieListAdapter(new ArrayList<MovieModel>(0), this);
        mMovieListAdapter.setOnTryAgainListener(this);

        final int itensPerRow = getItensPerRow();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mMovieListRecyclerView.getContext(), itensPerRow);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mMovieListAdapter.getItemViewType(position)) {
                    case MovieListAdapter.ITEM_TYPE_GRID_STATUS:
                        return itensPerRow;
                    default: // Normal item.
                        return 1;
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

        setHasOptionsMenu(true);

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
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort_movie_list) {
            requestListOrdenation();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            mPresenter.loadMovieList(true);
        }
        return super.onOptionsItemSelected(item);
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
    public void requestListOrdenation() {
        Context context = mMovieListRecyclerView.getContext();
        int selectedIndex = mPresenter.getSortListDef();
        CharSequence[] values = {
                context.getString(R.string.sortby_popular),
                context.getString(R.string.sortby_rating)
        };

        new MaterialDialog.Builder(context)
                .title(R.string.orderby_dialog_title)
                .items(values)
                .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        reloadListWithNewSort(which);
                        return true;
                    }
                })
                .positiveText(R.string.select)
                .negativeText(R.string.cancel)
                .show();
    }

    @Override
    public void changeSortTitle() {
        @StringRes int sortTitleRes = R.string.sortby_rating;
        if (mPresenter.getSortListDef() == POPULAR) {
            sortTitleRes = R.string.sortby_popular;
        }

        getActivity().setTitle(sortTitleRes);
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
    public void hideLoadingMovieListError() {
        mMovieListAdapter.hideErrorLoadingContent();
    }

    private void reloadListWithNewSort(@SortMovieListDescriptor.SortMovieListDef int sortMovieListEnum) {
        mPresenter.setOrderByEnum(sortMovieListEnum);
    }

    @Override
    public void tryAgain() {
        mPresenter.tryAgain();
    }

    @Override
    public void onClick(int index, MovieModel movieModel) {
        mPresenter.openMovieDetail(movieModel);
    }
}
