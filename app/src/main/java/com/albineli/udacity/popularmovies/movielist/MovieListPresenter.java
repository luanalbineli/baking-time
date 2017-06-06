package com.albineli.udacity.popularmovies.movielist;

import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Presenter of the movie list fragment.
 */

public class MovieListPresenter extends BasePresenterImpl implements MovieListContract.Presenter {
    private MovieListContract.View mView;
    private @MovieListFilterDescriptor.MovieListFilter
    int mFilter;

    private boolean mHasError = false;
    private Disposable mSubscription;
    /*
        The unitial page must be 1 (API implementation).
     */
    private int mPageIndex = 1;

    @Inject
    MovieListPresenter(@NonNull MovieRepository movieRepository) {
        super(movieRepository);

        mFilter = movieRepository.getMovieListSort(MovieListFilterDescriptor.POPULAR);
    }

    @Override
    public void setView(MovieListContract.View view) {
        mView = view;
    }

    @Override
    public void start(@MovieListFilterDescriptor.MovieListFilter int filter) {
        loadMovieList(true);
    }

    @Override
    public void onStop() {
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
        }
    }

    private void loadMovieList(final boolean startOver, @MovieListFilterDescriptor.MovieListFilter int filter) {
        if (mSubscription != null) {
            return;
        }

        mView.showLoadingIndicator();

        if (startOver) {
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }

        Observable<ArrayRequestAPI<MovieModel>> observable;
        if (filter == MovieListFilterDescriptor.POPULAR) {
            observable = mMovieRepository.getPopularList(mPageIndex);
        } else if (filter == MovieListFilterDescriptor.RATING) {
            observable = mMovieRepository.getTopRatedList(mPageIndex);
        } else { // TODO: FIX
            observable = mMovieRepository.getTopRatedList(mPageIndex);
            //observable = mMovieRepository.getFavoriteList();
        }

        mSubscription = observable.subscribe(
                response -> handleSuccessLoadMovieList(response, startOver),
                this::handleErrorLoadMovieList);
    }

    private void handleSuccessLoadMovieList(ArrayRequestAPI<MovieModel> response, boolean startOver) {
        if (mFilter == MovieListFilterDescriptor.FAVORITE) {
            mView.hideRequestStatus();
        } else {
            mView.showLoadingIndicator(); // Show again to draw again (and wrap content).
        }

        mSubscription = null;
        if (response.results.size() == 0) {
            mView.clearMovieList(); // Make sure that the list is empty.
            mView.showEmptyListMessage();
        } else {
            mView.showMovieList(response.results, startOver);
        }

        if (response.hasMorePages()) {
            mView.enableLoadMoreListener();
        } else {
            mView.disableLoadMoreListener();
        }
    }

    private void handleErrorLoadMovieList(Throwable throwable) {
        mSubscription = null;
        mHasError = true;
        Timber.e(throwable, "An error occurred while tried to get the movies");

        if (mPageIndex > 1) { // If something got wrong, reverse to the original position.
            mPageIndex--;
        }

        mView.showLoadingMovieListError();
    }

    @Override
    public void loadMovieList(final boolean startOver) {
        loadMovieList(startOver, mFilter);
    }

    @Override
    public void setFilter(@MovieListFilterDescriptor.MovieListFilter int movieListFilter) {
        if (mFilter == movieListFilter) { // If it's the same order, do nothing.
            return;
        }

        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
        }

        // Set the new order and save it.
        mFilter = movieListFilter;
        mMovieRepository.saveMovieListSort(mFilter);
        // Reload the movie list.
        loadMovieList(true);
    }

    @Override
    public void openMovieDetail(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);
    }

    @Override
    public void onListEndReached() {
        if (mHasError) {
            return;
        }
        loadMovieList(false);
    }

    @Override
    public void tryAgain() {
        mHasError = false;
        mView.hideRequestStatus();

        loadMovieList(false);
    }
}
