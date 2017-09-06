package com.udacity.bakingtime.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.injector.components.ApplicationComponent;


public abstract class BaseFragment<TView> extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent applicationComponent = BakingTimeApplication.getApplicationComponent(getActivity());
        onInjectDependencies(applicationComponent);

        getPresenterImplementation().setView(getViewImplementation());
    }

    protected abstract void onInjectDependencies(ApplicationComponent applicationComponent);

    protected abstract BasePresenter<TView> getPresenterImplementation();
    protected abstract TView getViewImplementation();
}
