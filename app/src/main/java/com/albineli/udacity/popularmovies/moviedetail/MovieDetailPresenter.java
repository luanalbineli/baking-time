package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailPresenter extends BasePresenterImpl implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;

    @Inject
    MovieDetailPresenter(@NonNull MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void start(MovieModel movieModel) {
        mView.showMovieDetail(movieModel);

        mMovieRepository.getMovieDetailById(movieModel.getId()).subscribe(movieModel1 -> mView.setFavoriteButtonState(true));

        mMovieRepository.getReviewsByMovieId(1, movieModel.getId()).subscribe(
                movieReviewModels -> mView.showMovieReview(movieReviewModels),
                throwable -> {
                    Timber.e(throwable, "An error occurred while tried to get the movie reviews");
                    mView.showErrorMessageLoadReviews();
                });
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
}
