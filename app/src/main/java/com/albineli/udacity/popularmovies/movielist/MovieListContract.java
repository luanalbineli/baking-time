package com.albineli.udacity.popularmovies.movielist;

import com.albineli.udacity.popularmovies.enums.SortMovieListEnum;
import com.albineli.udacity.popularmovies.model.MovieModel;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showLoadingMovieListError();
        void showMovieList(List<MovieModel> movieList, boolean replaceData);
        void requestListOrdenation();
        void changeSortTitle();
        void showMovieDetail(MovieModel movieModel);

        void hideLoadingMovieListError();
    }

    interface Presenter {
        void start();
        void onStop();
        void loadMovieList(boolean startOver);
        SortMovieListEnum getSortListEnum();
        void setOrderByEnum(SortMovieListEnum sortMovieListEnum);
        void openMovieDetail(MovieModel movieModel);
        void onListEndReached();

        void tryAgain();
    }
}
