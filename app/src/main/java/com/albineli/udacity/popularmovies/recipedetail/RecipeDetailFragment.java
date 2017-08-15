package com.albineli.udacity.popularmovies.recipedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.albineli.udacity.popularmovies.recipedetail.steplist.RecipeStepListFragment;

import java.security.InvalidParameterException;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class RecipeDetailFragment extends BaseFragment<RecipeDetailContract.View> implements RecipeDetailContract.View {
    public static RecipeDetailFragment getInstance(RecipeModel recipeModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_MODEL_BUNDLE_KEY, recipeModel);

        RecipeDetailFragment movieDetailFragment = new RecipeDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    protected BasePresenter<RecipeDetailContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeDetailContract.View getViewImplementation() {
        return this;
    }

    private static String RECIPE_MODEL_BUNDLE_KEY = "recipe_model";

    @Inject
    RecipeDetailPresenter mPresenter;

    private RecipeModel mRecipeModel;

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
        if (getArguments() == null || !getArguments().containsKey(RECIPE_MODEL_BUNDLE_KEY)) {
            throw new InvalidParameterException("movie");
        }
        mRecipeModel = getArguments().getParcelable(RECIPE_MODEL_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(mRecipeModel.getName());

        setupViewPager(view);

        mPresenter.start(mRecipeModel);
    }

    private void setupViewPager(View view) {
        ViewPager viewPager = ButterKnife.findById(view, R.id.vpRecipeDetail);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mRecipeModel, getActivity()));

        TabLayout tabLayout = ButterKnife.findById(view, R.id.tlRecipeDetail);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final RecipeModel mRecipeModel;
        private final String[] mTitles;

        ViewPagerAdapter(FragmentManager fm, RecipeModel recipeModel, Context context) {
            super(fm);


            mTitles = new String[]{context.getString(R.string.ingredients), context.getString(R.string.steps)};
            mRecipeModel = recipeModel;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return RecipeIngredientListFragment.getInstance(mRecipeModel.getIngredientList());
            }
            return RecipeStepListFragment.getInstance(mRecipeModel.getRecipeStepList());
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
