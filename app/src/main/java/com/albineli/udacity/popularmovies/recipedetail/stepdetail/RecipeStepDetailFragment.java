package com.albineli.udacity.popularmovies.recipedetail.stepdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.event.SelectStepEvent;
import com.albineli.udacity.popularmovies.exoplayer.ExoPlayerFragment;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends BaseFragment<RecipeStepDetailContract.View> implements RecipeStepDetailContract.View {

    @BindView(R.id.tvStepDetailDescription)
    TextView mDescriptionTextView;
    private ExoPlayerFragment mExoPlayerFragment;


    @BindView(R.id.tvStepDetailShortDescription)
    TextView mShortDescriptionTextView;


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

        mExoPlayerFragment = (ExoPlayerFragment) getChildFragmentManager().findFragmentById(R.id.fragmentExoPlayer);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mPresenter.start(mRecipeModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectStepEvent(SelectStepEvent selectStepEvent) {
        mPresenter.onSelectStep(selectStepEvent.recipeStepModel);
    }

    @Override
    public void showStepDetail(RecipeStepModel recipeStepModel) {
        mShortDescriptionTextView.setText(recipeStepModel.getShortDescription());
        if (recipeStepModel.getDescripton() == null || recipeStepModel.getDescripton().equals(recipeStepModel.getShortDescription())) {
            mDescriptionTextView.setVisibility(View.GONE);
        } else {
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(recipeStepModel.getDescripton());
        }

        if (recipeStepModel.getRealVideoUrl() != null) {
            mExoPlayerFragment.setVisibility(View.VISIBLE);
            mExoPlayerFragment.setVideoUrl(recipeStepModel.getRealVideoUrl());
        } else {
            mExoPlayerFragment.setVisibility(View.GONE);
        }
    }
}
