package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.injector.modules.MovieListModule;
import com.albineli.udacity.popularmovies.movielist.MovieListContract;
import com.albineli.udacity.popularmovies.movielist.MovieListFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
    MovieListContract.Presenter presenter();
}
