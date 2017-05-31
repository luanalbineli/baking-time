package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.mainactivity.MainActivity;
import com.albineli.udacity.popularmovies.injector.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
