package com.albineli.udacity.popularmovies.injector.components;


import android.content.SharedPreferences;

import com.albineli.udacity.popularmovies.injector.modules.ApplicationModule;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit retrofit();

    SharedPreferences sharedPreferences();
}
