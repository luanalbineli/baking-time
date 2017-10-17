package com.udacity.bakingtime;


import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerApplicationComponent;
import com.udacity.bakingtime.injector.modules.ApplicationModule;

import timber.log.Timber;

public class BakingTimeApplication extends Application {
    protected ApplicationComponent mApplicationComponent;

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

        // LeakCanary
        LeakCanary.install(this);

        // Dagger2
        mApplicationComponent = buildComponent();

        // Picasso.
        Picasso picasso = new Picasso.Builder(this).build();
        picasso.setLoggingEnabled(BuildConfig.DEBUG);
        Picasso.setSingletonInstance(picasso);

        // Vector compat.
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((BakingTimeApplication) context.getApplicationContext()).getApplicationComponent();
    }
}
