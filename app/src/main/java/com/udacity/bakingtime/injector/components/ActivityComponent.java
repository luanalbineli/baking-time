package com.udacity.bakingtime.injector.components;


import com.udacity.bakingtime.injector.PerActivity;
import com.udacity.bakingtime.mainactivity.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
