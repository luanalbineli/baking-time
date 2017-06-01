package com.albineli.udacity.popularmovies.repository.movie;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.TrailerModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.RepositoryBase;
import com.albineli.udacity.popularmovies.repository.data.MovieContract;
import com.albineli.udacity.popularmovies.util.IWantToUseKotlinAndUnitINSTANCE;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MovieRepository extends RepositoryBase {
    private static IMovieService mMovieService;
    private static String MOVIE_LIST_FILTER_KEY = "movie_list_filter";
    private static final String SP_KEY = "sp_popular_movies";

    private final Retrofit mRetrofit;
    private final SharedPreferences mSharedPreferences;
    private final PopularMovieApplication mApplicationContext;

    @Inject
    MovieRepository(Retrofit retrofit, PopularMovieApplication applicationContext) {
        mRetrofit = retrofit;
        mApplicationContext = applicationContext;
        mSharedPreferences = applicationContext.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
    }

    private IMovieService getMovieServiceInstance() {
        if (mMovieService == null) {
            mMovieService = mRetrofit.create(IMovieService.class);
        }
        return mMovieService;
    }

    public void saveMovieListSort(@MovieListFilterDescriptor.MovieListFilter int movieListFilter) {
        mSharedPreferences.edit().putInt(MOVIE_LIST_FILTER_KEY, movieListFilter).apply();
    }

    public @MovieListFilterDescriptor.MovieListFilter int getMovieListSort(@MovieListFilterDescriptor.MovieListFilter int movieListFilter) {
        int intSortList = mSharedPreferences.getInt(MOVIE_LIST_FILTER_KEY, movieListFilter);
        return MovieListFilterDescriptor.parseFromInt(intSortList);
    }

    public Observable<List<MovieModel>> getTopRatedList(final int pageIndex) {
        return reduceMovieList(getMovieServiceInstance().getTopRatedList(pageIndex));
    }

    public Observable<List<MovieModel>> getPopularList(final int pageIndex) {
        return reduceMovieList(getMovieServiceInstance().getPopularList(pageIndex));
    }

    public Observable<List<MovieReviewModel>> getReviewsByMovieId(int pageIndex, int movieId) {
        return observeOnMainThread(getMovieServiceInstance().getReviewsByMovieId(movieId, pageIndex).map(listArrayRequestAPI -> listArrayRequestAPI.results));
    }

    public Observable<List<TrailerModel>> getTrailersByMovieId(int pageIndex, int movieId) {
        return observeOnMainThread(getMovieServiceInstance().getTrailersByMovieId(movieId, pageIndex).map(listArrayRequestAPI -> listArrayRequestAPI.results));
    }

    public Observable<List<MovieModel>> getFavoriteList() {
        return observeOnMainThread(Observable.create((ObservableOnSubscribe<List<MovieModel>>) emitter -> {
            final ContentResolver contentResolver = mApplicationContext.getContentResolver();
            if (contentResolver == null) {
                emitter.onError(new RuntimeException("Cannot get the ContentResolver"));
                return;
            }

            final Cursor cursor = contentResolver.query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            if (cursor == null) {
                emitter.onError(new SQLDataException("An internal error occurred."));
                return;
            }

            List<MovieModel> favoriteMovieModelList = new ArrayList<>(cursor.getCount());
            try {
                while (cursor.moveToNext()) {
                    favoriteMovieModelList.add(MovieModel.fromCursor(cursor));
                }

                emitter.onNext(favoriteMovieModelList);
            } catch (Exception ex) {
                emitter.onError(ex);
            } finally {
                cursor.close();
            }
        }).subscribeOn(Schedulers.io()));
    }

    private Observable<List<MovieModel>> reduceMovieList(Observable<ArrayRequestAPI<List<MovieModel>>> observable) {
        Observable<List<MovieModel>> reducedObservable = observable.map(listArrayRequestAPI -> listArrayRequestAPI.results);

        return observeOnMainThread(reducedObservable);
    }

    public Observable<IWantToUseKotlinAndUnitINSTANCE> removeFavoriteMovie(final MovieModel movieModel) {
        return observeOnMainThread(Observable.create((ObservableOnSubscribe<IWantToUseKotlinAndUnitINSTANCE>) emitter -> {
            final ContentResolver contentResolver = mApplicationContext.getContentResolver();
            if (contentResolver == null) {
                emitter.onError(new RuntimeException("Cannot get the ContentResolver"));
                return;
            }

            final int numberOfRemovedItems = contentResolver.delete(MovieContract.MovieEntry.buildMovieWithId(movieModel.getId()), null, null);
            if (numberOfRemovedItems != 1) {
                emitter.onError(new SQLDataException("An internal error occurred."));
                return;
            }

            emitter.onNext(IWantToUseKotlinAndUnitINSTANCE.NOW);
        }).subscribeOn(Schedulers.io()));
    }

    public Observable<IWantToUseKotlinAndUnitINSTANCE> saveFavoriteMovie(final MovieModel movieModel) {
        return observeOnMainThread(Observable.create((ObservableOnSubscribe<IWantToUseKotlinAndUnitINSTANCE>) emitter -> {
            final ContentResolver contentResolver = mApplicationContext.getContentResolver();
            if (contentResolver == null) {
                emitter.onError(new RuntimeException("Cannot get the ContentResolver"));
                return;
            }

            final Uri uri = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, movieModel.toContentValues());
            if (uri == null) {
                emitter.onError(new SQLDataException("An internal error occurred."));
                return;
            }

            emitter.onNext(IWantToUseKotlinAndUnitINSTANCE.NOW);
        }).subscribeOn(Schedulers.io()));
    }

    public Observable<MovieModel> getMovieDetailById(final int id) {
        return observeOnMainThread(Observable.create((ObservableOnSubscribe<MovieModel>) emitter -> {
            final ContentResolver contentResolver = mApplicationContext.getContentResolver();
            if (contentResolver == null) {
                emitter.onError(new RuntimeException("Cannot get the ContentResolver"));
                return;
            }

            final Cursor cursor = contentResolver.query(MovieContract.MovieEntry.buildMovieWithId(id), null, null, null, null);
            if (cursor == null) {
                emitter.onError(new SQLDataException("An internal error occurred."));
                return;
            }

            try {
                if (cursor.moveToNext()) {
                    emitter.onNext(MovieModel.fromCursor(cursor));
                }
            } catch (Exception ex) {
                emitter.onError(ex);
            } finally {
                cursor.close();
            }
        }).subscribeOn(Schedulers.io()));
    }
}
