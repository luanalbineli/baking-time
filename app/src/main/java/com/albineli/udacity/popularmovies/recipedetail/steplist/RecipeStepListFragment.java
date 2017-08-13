package com.albineli.udacity.popularmovies.recipedetail.steplist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.base.BaseRecyclerViewFragment;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeStepModel;
import com.viewpagerindicator.TitlePageIndicator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepListFragment extends BaseFragment<RecipeStepListContract.View> implements RecipeStepListContract.View {
    public static RecipeStepListFragment getInstance(List<RecipeStepModel> recipeStepList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPE_STEP_LIST_BUNDLE_KEY, new ArrayList<>(recipeStepList));

        RecipeStepListFragment stepListFragment = new RecipeStepListFragment();
        stepListFragment.setArguments(bundle);
        return stepListFragment;
    }

    @Override
    protected BasePresenter<RecipeStepListContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeStepListContract.View getViewImplementation() {
        return this;
    }

    private static String RECIPE_STEP_LIST_BUNDLE_KEY = "step_list";

    @Inject
    RecipeStepListPresenter mPresenter;

    @BindView(R.id.indicatorRecipeStepList)
    TitlePageIndicator mIndicator;

    @BindView(R.id.vpRecipeStepList)
    ViewPager mViewPager;

    private List<RecipeStepModel> mRecipeStepList;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(RECIPE_STEP_LIST_BUNDLE_KEY)) {
            throw new InvalidParameterException("step_list");
        }
        mRecipeStepList = getArguments().getParcelableArrayList(RECIPE_STEP_LIST_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start(mRecipeStepList);
    }
}
