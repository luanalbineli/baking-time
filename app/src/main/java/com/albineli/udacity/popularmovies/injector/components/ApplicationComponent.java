package com.albineli.udacity.popularmovies.injector.components;


import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit retrofit();
    PopularMovieApplication popularMovieApplicationContext();
}
