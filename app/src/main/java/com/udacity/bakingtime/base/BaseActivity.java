package com.udacity.bakingtime.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.injector.components.ApplicationComponent;


public abstract class BaseActivity<TView> extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_center_title_activity);

        ApplicationComponent applicationComponent = BakingTimeApplication.getApplicationComponent(this);
        onInjectDependencies(applicationComponent);

        getPresenterImplementation().setView(getViewImplementation());
    }

    protected abstract void onInjectDependencies(ApplicationComponent applicationComponent);

    protected abstract BasePresenter<TView> getPresenterImplementation();
    protected abstract TView getViewImplementation();
}
