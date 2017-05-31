package com.albineli.udacity.popularmovies.moviedetail;


import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;
import com.albineli.udacity.popularmovies.util.IWantToUseKotlinAndUnitINSTANCE;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
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

        mMovieRepository.getMovieDetailById(movieModel.getId()).subscribe(new Consumer<MovieModel>() {
            @Override
            public void accept(MovieModel movieModel) throws Exception {
                mView.setFavoriteButtonState(true);
            }
        });
    }

    @Override
    public void setView(MovieDetailContract.View view) {
        mView = view;
    }

    @Override
    public void removeFavoriteMovie(MovieModel movieModel) {
        mMovieRepository.removeFavoriteMovie(movieModel).subscribe(new Consumer<IWantToUseKotlinAndUnitINSTANCE>() {
            @Override
            public void accept(IWantToUseKotlinAndUnitINSTANCE now) throws Exception {
                mView.showSuccessMessageRemoveFavoriteMovie();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable, "An error occurred while tried to remove the favorite movie");
                mView.showErrorMessageRemoveFavoriteMovie();
            }
        });
    }

    @Override
    public void saveFavoriteMovie(MovieModel movieModel) {
        mMovieRepository.saveFavoriteMovie(movieModel).subscribe(new Consumer<IWantToUseKotlinAndUnitINSTANCE>() {
            @Override
            public void accept(IWantToUseKotlinAndUnitINSTANCE now) throws Exception {
                mView.showSuccessMessageAddFavoriteMovie();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable, "An error occurred while tried to add the favorite movie");
                mView.showErrorMessageAddFavoriteMovie();
            }
        });
    }
}
