package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {
    private MovieDetailContract.View mView;
    private ArrayRequestAPI<MovieReviewModel> mMovieReviewRequest;
    private List<MovieTrailerModel> mMovieTrailerList;
    private int mMovieId;

    @Inject
    MovieDetailPresenter(@NonNull MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void start(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);

        // If it is in the database, means that is favorite.
        mMovieRepository.getMovieDetailById(movieModel.getId()).subscribe(movieModel1 -> mView.setFavoriteButtonState(true));

        loadMovieReviews(movieModel.getId());

        loadMovieTrailers(movieModel.getId());

        mMovieId = movieModel.getId();
    }

    private void loadMovieTrailers(int movieId) {
        mView.showLoadingTrailersIndicator();
        mMovieRepository.getTrailersByMovieId(movieId).subscribe(
                this::handleMovieTrailerRequestSuccess,
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to get the movie trailers");
                    mView.showErrorMessageLoadTrailers();
                });
    }

    private void loadMovieReviews(int movieId) {
        mView.showLoadingReviewsIndicator();
        mMovieRepository.getReviewsByMovieId(1, movieId).subscribe(
                this::handleMovieReviewRequestSuccess,
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to get the movie reviews");
                    mView.showErrorMessageLoadReviews();
                });
    }

    private void handleMovieTrailerRequestSuccess(List<MovieTrailerModel> movieTrailerModels) {
        if (movieTrailerModels.size() == 0) {
            mView.showEmptyTrailerListMessage();
            mView.setShowAllTrailersButtonVisibility(false);
            return;
        }

        mMovieTrailerList = movieTrailerModels;
        if (mMovieTrailerList.size() > 2) {
            mView.showMovieTrailer(mMovieTrailerList.subList(0, 2));
            mView.setShowAllTrailersButtonVisibility(true);
        } else {
            mView.showMovieTrailer(mMovieTrailerList);
            mView.setShowAllTrailersButtonVisibility(false);
        }
    }

    private void handleMovieReviewRequestSuccess(ArrayRequestAPI<MovieReviewModel> movieReviewModelArrayRequestAPI) {
        if (movieReviewModelArrayRequestAPI.results.size() == 0) {
            mView.showEmptyReviewListMessage();
            mView.setShowAllReviewsButtonVisibility(false);
            return;
        }
        mMovieReviewRequest = movieReviewModelArrayRequestAPI;
        if (movieReviewModelArrayRequestAPI.results.size() > 2) {
            mView.showMovieReview(mMovieReviewRequest.results.subList(0, 2));
            mView.setShowAllReviewsButtonVisibility(true);
        } else {
            mView.showMovieReview(mMovieReviewRequest.results);
            mView.setShowAllReviewsButtonVisibility(false);
        }
    }

    @Override
    public void setView(MovieDetailContract.View view) {
        mView = view;
    }

    @Override
    public void removeFavoriteMovie(MovieModel movieModel) {
        mMovieRepository.removeFavoriteMovie(movieModel).subscribe(
                now -> mView.showSuccessMessageRemoveFavoriteMovie(),
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to remove the favorite movie");
                    mView.showErrorMessageRemoveFavoriteMovie();
                    mView.setFavoriteButtonState(true);
                });
    }

    @Override
    public void saveFavoriteMovie(MovieModel movieModel) {
        mMovieRepository.saveFavoriteMovie(movieModel).subscribe(
                now -> mView.showSuccessMessageAddFavoriteMovie(),
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to add the favorite movie");
                    mView.showErrorMessageAddFavoriteMovie();
                    mView.setFavoriteButtonState(false);
                });
    }

    @Override
    public void showAllReviews() {
        mView.showAllReviews(mMovieReviewRequest.results, mMovieReviewRequest.hasMorePages());
    }

    @Override
    public void showAllTrailers() {
        mView.showAllTrailers(mMovieTrailerList);
    }

    @Override
    public void tryToLoadTrailersAgain() {
        loadMovieTrailers(mMovieId);
    }

    @Override
    public void tryToLoadReviewAgain() {
        loadMovieReviews(mMovieId);
    }
}
