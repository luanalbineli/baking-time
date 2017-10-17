package com.udacity.bakingtime.recipedetail.stepdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseFragment;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.exoplayer.ExoPlayerFragment;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeStepModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends BaseFragment<RecipeStepDetailContract.View> implements RecipeStepDetailContract.View {

    @BindView(R.id.tvStepDetailDescription)
    TextView mDescriptionTextView;

    private ExoPlayerFragment mExoPlayerFragment;

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
    public void showStepDetail(RecipeStepModel recipeStepModel) {
        if (recipeStepModel.getDescripton() == null || recipeStepModel.getDescripton().equals(recipeStepModel.getShortDescription())) {
            mDescriptionTextView.setVisibility(View.GONE);
        } else {
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(recipeStepModel.getDescripton());
        }

        if (recipeStepModel.getVideoURL() != null) {
            mExoPlayerFragment.setVisibility(View.VISIBLE);
            mExoPlayerFragment.showVideo(recipeStepModel.getVideoURL(), recipeStepModel.getThumbnailUrl());
        } else {
            mExoPlayerFragment.setVisibility(View.GONE);
        }
    }
}
