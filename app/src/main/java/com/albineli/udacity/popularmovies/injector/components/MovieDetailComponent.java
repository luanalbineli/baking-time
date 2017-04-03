package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.injector.modules.MovieDetailModule;
import com.albineli.udacity.popularmovies.injector.modules.MovieListModule;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailContract;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailFragment;
import com.albineli.udacity.popularmovies.movielist.MovieListContract;
import com.albineli.udacity.popularmovies.movielist.MovieListFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailFragment fragment);
    MovieDetailContract.Presenter presenter();
}
