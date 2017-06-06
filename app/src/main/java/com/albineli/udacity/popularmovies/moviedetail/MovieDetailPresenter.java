package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {
    private MovieDetailContract.View mView;
    private List<MovieReviewModel> mMovieReviewList;

    @Inject
    MovieDetailPresenter(@NonNull MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void start(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);

        mMovieRepository.getMovieDetailById(movieModel.getId()).subscribe(movieModel1 -> mView.setFavoriteButtonState(true));

        mView.showLoadingReviewsIndicator();
        mMovieRepository.getReviewsByMovieId(1, movieModel.getId()).subscribe(
                this::handleMovieReviewRequestSuccess,
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to get the movie reviews");
                    mView.showErrorMessageLoadReviews();
                });
    }

    private void handleMovieReviewRequestSuccess(List<MovieReviewModel> movieReviewList) {
        mMovieReviewList = movieReviewList;
        if (movieReviewList.size() > 2) {
            mView.showMovieReview(mMovieReviewList.subList(0, 2));
            mView.setShowAllReviewsButtonVisibility(true);
        } else {
            mView.showMovieReview(mMovieReviewList);
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
        mView.showAllReviews(mMovieReviewList);
    }
}
