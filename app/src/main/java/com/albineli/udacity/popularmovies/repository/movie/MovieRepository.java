package com.albineli.udacity.popularmovies.repository.movie;


import android.content.SharedPreferences;

import com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.RepositoryBase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

import static com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor.POPULAR;
import static com.albineli.udacity.popularmovies.enums.SortMovieListDescriptor.RATING;

public class MovieRepository extends RepositoryBase {
    private static IMovieService mMovieService;
    private static String MOVIE_LIST_SORT_KEY = "movie_list_sort";

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;

    @Inject
    public MovieRepository(Retrofit retrofit, SharedPreferences sharedPreferences) {
        mRetrofit = retrofit;
        mSharedPreferences = sharedPreferences;
    }

    private IMovieService getMovieServiceInstance() {
        if (mMovieService == null) {
            mMovieService = mRetrofit.create(IMovieService.class);
        }
        return mMovieService;
    }

    public void saveMovieListSort(@SortMovieListDescriptor.SortMovieListDef int sortMovieListDef) {
        mSharedPreferences.edit().putInt(MOVIE_LIST_SORT_KEY, sortMovieListDef).apply();
    }

    public @SortMovieListDescriptor.SortMovieListDef int getMovieListSort(@SortMovieListDescriptor.SortMovieListDef int defaultSort) {
        int sort = mSharedPreferences.getInt(MOVIE_LIST_SORT_KEY, defaultSort);
        if (sort == RATING) {
            return RATING;
        }

        return POPULAR;
    }

    public Observable<List<MovieModel>> getTopRatedList(final int pageIndex) {
        return reduceMovieList(getMovieServiceInstance().getTopRatedList(pageIndex));
    }

    public Observable<List<MovieModel>> getFavoriteList(final int pageIndex) {
        return reduceMovieList(getMovieServiceInstance().getTopRatedList(pageIndex));
    }

    public Observable<List<MovieModel>> getPopularList(final int pageIndex) {
        return reduceMovieList(getMovieServiceInstance().getPopularList(pageIndex));
    }

    private Observable<List<MovieModel>> reduceMovieList(Observable<ArrayRequestAPI<List<MovieModel>>> observable) {
        Observable<List<MovieModel>> reducedObservable = observable.map(new Function<ArrayRequestAPI<List<MovieModel>>, List<MovieModel>>() {
            @Override
            public List<MovieModel> apply(@NonNull ArrayRequestAPI<List<MovieModel>> listArrayRequestAPI) throws Exception {
                return listArrayRequestAPI.results;
            }
        });

        return observeOnMainThread(reducedObservable);
    }
}
