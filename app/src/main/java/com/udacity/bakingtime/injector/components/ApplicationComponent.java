package com.udacity.bakingtime.injector.components;

import com.google.gson.Gson;
import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit retrofit();
    Gson gson();
    BakingTimeApplication popularMovieApplicationContext();
}
