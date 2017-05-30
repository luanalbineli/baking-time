package com.albineli.udacity.popularmovies.movielist;

import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Presenter of the movie list fragment.
 */

public class MovieListPresenter extends BasePresenterImpl implements MovieListContract.Presenter {
    private MovieListContract.View mView;
    private @MovieListFilterDescriptor.MovieListFilter int mFilter;

    private boolean mIsLoadingMovieList = false;
    private boolean mHasError = false;
    private Disposable mSubscription;
    /*
        The unitial page must be 1 (API implementation).
     */
    private int mPageIndex = 1;

    @Inject
    public MovieListPresenter(@NonNull MovieRepository movieRepository) {
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

        mIsLoadingMovieList = false;
    }

    private void loadMovieList(final boolean startOver, @MovieListFilterDescriptor.MovieListFilter int filter) {
        mIsLoadingMovieList = true;

        if (startOver) {
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }

        Observable<List<MovieModel>> observable;
        if (filter == MovieListFilterDescriptor.POPULAR) {
            observable = mMovieRepository.getPopularList(mPageIndex);
        } else if (filter == MovieListFilterDescriptor.RATING) {
            observable = mMovieRepository.getTopRatedList(mPageIndex);
        } else {
            observable = mMovieRepository.getFavoriteList();
        }

        mSubscription = observable.subscribe(new Consumer<List<MovieModel>>() {
            @Override
            public void accept(@NonNull List<MovieModel> movieModels) {
                mIsLoadingMovieList = false;
                if (movieModels.size() == 0) {
                    mView.clearMovieList(); // Make sure that the list is empty.
                    mView.showEmptyListMessage();
                } else {
                    mView.showMovieList(movieModels, startOver);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) {
                mIsLoadingMovieList = false;
                mHasError = true;
                Timber.e(throwable, "An error occurred while tried to get the movies");

                if (mPageIndex > 1) { // If something got wrong, reverse to the original position.
                    mPageIndex--;
                }

                mView.showLoadingMovieListError();
            }
        });
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
        if (mIsLoadingMovieList || mHasError) {
            return;
        }
        loadMovieList(false);
    }

    @Override
    public void tryAgain() {
        mHasError = false;
        mView.hideLoadingMovieListError();

        loadMovieList(false);
    }
}
