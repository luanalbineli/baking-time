package com.albineli.udacity.popularmovies;


import android.app.Application;
import android.content.Context;

import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerApplicationComponent;
import com.albineli.udacity.popularmovies.injector.modules.ApplicationModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class PopularMovieApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        // Timber
        Timber.plant(new Timber.DebugTree());
        // Fresco
        Fresco.initialize(this);
        // LeakCanary
        LeakCanary.install(this);
        // Dagger2
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((PopularMovieApplication) context.getApplicationContext()).getApplicationComponent();
    }
}
