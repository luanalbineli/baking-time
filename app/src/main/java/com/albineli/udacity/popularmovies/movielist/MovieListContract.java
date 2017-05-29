package com.albineli.udacity.popularmovies.movielist;

import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showLoadingMovieListError();
        void showMovieList(List<MovieModel> movieList, boolean replaceData);
        void showMovieDetail(MovieModel movieModel);
        void showEmptyListMessage();
        void hideLoadingMovieListError();
    }

    interface Presenter extends BasePresenter<View> {
        void start(@MovieListFilterDescriptor.MovieListFilter int filter);
        void onStop();
        void loadMovieList(boolean startOver);
        void setFilter(@MovieListFilterDescriptor.MovieListFilter int filter);
        void openMovieDetail(MovieModel movieModel);
        void onListEndReached();

        void tryAgain();
    }
}
