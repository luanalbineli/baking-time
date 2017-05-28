package com.albineli.udacity.popularmovies.movielist;

import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showLoadingMovieListError();
        void showMovieList(List<MovieModel> movieList, boolean replaceData);
        void showMovieDetail(MovieModel movieModel);

        void hideLoadingMovieListError();
    }

    interface Presenter {
        void start(@MovieListFilterDescriptor.MovieListFilter int filter);
        void onStop();
        void loadMovieList(boolean startOver);
        @SortMovieListDescriptor.SortMovieListDef int getSortListDef();
        void setOrderByEnum(int sortMovieListEnum);
        void openMovieDetail(MovieModel movieModel);
        void onListEndReached();

        void tryAgain();
    }
}
