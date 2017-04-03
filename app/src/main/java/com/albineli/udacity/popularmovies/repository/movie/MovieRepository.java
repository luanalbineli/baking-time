package com.albineli.udacity.popularmovies.repository.movie;


import android.content.SharedPreferences;

import com.albineli.udacity.popularmovies.enums.SortMovieListEnum;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.RepositoryBase;

import java.security.InvalidKeyException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

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

    public void saveMovieListSort(SortMovieListEnum sortMovieListEnum) {
        mSharedPreferences.edit().putInt(MOVIE_LIST_SORT_KEY, sortMovieListEnum.ordinal()).apply();
    }

    public SortMovieListEnum getMovieListSort(SortMovieListEnum defaultSort) {
        int sort = mSharedPreferences.getInt(MOVIE_LIST_SORT_KEY, defaultSort.ordinal());
        return SortMovieListEnum.values()[sort];
    }

    public Observable<List<MovieModel>> getTopRatedList(final int pageIndex) {
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
