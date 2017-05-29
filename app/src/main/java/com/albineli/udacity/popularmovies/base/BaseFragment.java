package com.albineli.udacity.popularmovies.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;

public abstract class BaseFragment<TView> extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent applicationComponent = PopularMovieApplication.getApplicationComponent(getActivity());
        onInjectDependencies(applicationComponent);

        getPresenterImplementation().setView(getViewImplementation());
    }

    protected abstract void onInjectDependencies(ApplicationComponent applicationComponent);

    protected abstract BasePresenter<TView> getPresenterImplementation();
    protected abstract TView getViewImplementation();
}
