package com.albineli.udacity.popularmovies.recipedetail.stepdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends BaseFragment<RecipeStepDetailContract.View> implements RecipeStepDetailContract.View {

    @Override
    protected BasePresenter<RecipeStepDetailContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeStepDetailContract.View getViewImplementation() {
        return this;
    }

    @Inject
    RecipeStepDetailPresenter mPresenter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mPresenter.start(mRecipeModel);
    }

    public void showStepDetail(RecipeStepModel recipeStepModel) {

    }
}
