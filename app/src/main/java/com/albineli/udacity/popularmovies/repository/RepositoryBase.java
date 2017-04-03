package com.albineli.udacity.popularmovies.repository;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public abstract class RepositoryBase {
    protected <T> Observable<T> observeOnMainThread(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
    }
}
