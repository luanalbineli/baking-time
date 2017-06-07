package com.albineli.udacity.popularmovies.moviedetail.review;

import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MovieReviewListDialogPresenter implements MovieReviewListDialogContract.Presenter {
    private final MovieRepository mMovieRepository;

    private MovieReviewListDialogContract.View mView;

    private int mPageIndex;
    private Disposable mSubscription;
    private int mMovieId;

    @Inject
    public MovieReviewListDialogPresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @Override
    public void setView(MovieReviewListDialogContract.View view) {
        mView = view;
    }


    @Override
    public void start(List<MovieReviewModel> movieReviewList, int movieId, boolean hasMore) {
        mView.addReviewsToList(movieReviewList);
        if (hasMore) {
            mView.enableLoadMoreListener();
        }

        mMovieId = movieId;
    }

    @Override
    public void onListEndReached() {
        if (mSubscription != null) {
            return;
        }

        mView.showLoadingIndicator();

        mPageIndex++;

        Observable<ArrayRequestAPI<MovieReviewModel>> observable = mMovieRepository.getReviewsByMovieId(mPageIndex, mMovieId);

        mSubscription = observable.subscribe(
                this::handleSuccessLoadMovieReview,
                this::handleErrorLoadMovieReview);
    }

    private void handleSuccessLoadMovieReview(ArrayRequestAPI<MovieReviewModel> response) {
        mView.addReviewsToList(response.results);
        if (!response.hasMorePages()) {
            mView.disableLoadMoreListener();
        }
    }

    private void handleErrorLoadMovieReview(Throwable throwable) {
        Timber.e(throwable, "An error occurred while tried to get the movie reviews for page: " + mPageIndex);
        mPageIndex--;
        mView.showErrorLoadingReviews();
    }
}
