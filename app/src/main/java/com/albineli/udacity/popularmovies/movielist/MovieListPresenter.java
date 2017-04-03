package com.albineli.udacity.popularmovies.movielist;

import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.albineli.udacity.popularmovies.util.LogUtils.LOGE;
import static com.albineli.udacity.popularmovies.util.LogUtils.makeLogTag;

/**
 * Presenter of the movie list fragment.
 */

public class MovieListPresenter extends BasePresenterImpl implements MovieListContract.Presenter {
    private static final String TAG = makeLogTag(MovieListPresenter.class);

    private final MovieListContract.View mView;
    private @SortMovieListDescriptor.SortMovieListDef int mSortMovieListDef;

    private boolean mIsLoadingMovieList = false;
    private boolean mHasError = false;
    private Disposable mSubscription;
    /*
        The unitial page must be 1 (API implementation).
     */
    private int mPageIndex = 1;

    public MovieListPresenter(@NonNull MovieListContract.View view, @NonNull MovieRepository movieRepository) {
        super(movieRepository);
        mView = view;

        mSortMovieListDef = movieRepository.getMovieListSort(SortMovieListDescriptor.POPULAR);
    }

    @Override
    public void start() {
        loadMovieList(true);

        mView.changeSortTitle();
    }

    @Override
    public void onStop() {
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
        }

        mIsLoadingMovieList = false;
    }

    @Override
    public void loadMovieList(final boolean startOver) {
        mIsLoadingMovieList = true;

        if (startOver) {
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }

        Observable<List<MovieModel>> observable;
        if (mSortMovieListDef == SortMovieListDescriptor.POPULAR) {
            observable = mMovieRepository.getPopularList(mPageIndex);
        } else {
            observable = mMovieRepository.getTopRatedList(mPageIndex);
        }

        mSubscription = observable.subscribe(new Consumer<List<MovieModel>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull List<MovieModel> movieModels) {
                mIsLoadingMovieList = false;
                mView.showMovieList(movieModels, startOver);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) {
                mIsLoadingMovieList = false;
                mHasError = true;
                LOGE(TAG, "An error occurred while tried to get the movies", throwable);

                if (mPageIndex > 1) { // If something got wrong, reverse to the original position.
                    mPageIndex--;
                }

                mView.showLoadingMovieListError();
            }
        });
    }

    @Override
    public @SortMovieListDescriptor.SortMovieListDef int getSortListDef() {
        return mSortMovieListDef;
    }

    @Override
    public void setOrderByEnum(int sortMovieListEnum) {
        if (mSortMovieListDef == sortMovieListEnum) { // If it's the same order, do nothing.
            return;
        }

        // Set the new order and save it.
        mSortMovieListDef = sortMovieListEnum;
        mMovieRepository.saveMovieListSort(mSortMovieListDef);
        // Change the title.
        mView.changeSortTitle();
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
