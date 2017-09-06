package com.udacity.bakingtime.recipedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseFragment;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.udacity.bakingtime.recipedetail.stepdetail.RecipeStepDetailFragment;
import com.udacity.bakingtime.recipedetail.steplist.RecipeStepListFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;


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
        if (getArguments() != null && getArguments().containsKey(RECIPE_MODEL_BUNDLE_KEY)) {
            mRecipeModel = getArguments().getParcelable(RECIPE_MODEL_BUNDLE_KEY);
        }
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

        mPresenter.start(mRecipeModel);
    }

    @Override
    public void showRecipeDetailContent(RecipeModel recipeModel) {
        boolean useMasterDetail = getActivity().getResources().getBoolean(R.bool.useMasterDetail);
        if (useMasterDetail) {
            configureMasterDetailLayout(recipeModel);
        } else {
            configureDefaultLayout(recipeModel);
        }
    }

    private void configureDefaultLayout(RecipeModel recipeModel) {
        if (!TextUtils.isEmpty(recipeModel.getImage()) && getView() != null) {
            // TODO: implementation to fetch the image.
            ImageView ingredientImage = ButterKnife.findById(getView(), R.id.ivRecipeItemPicture);
        }
        setupViewPager(recipeModel);
    }

    private void configureMasterDetailLayout(RecipeModel recipeModel) {
        if (getView() == null) {
            Timber.wtf("Null view?");
            return;
        }

        RecipeStepListFragment recipeStepListFragment = (RecipeStepListFragment) getChildFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepList);
        recipeStepListFragment.setStepList(recipeModel.getRecipeStepList());

        RecipeStepDetailFragment recipeStepDetailFragment = (RecipeStepDetailFragment) getChildFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepDetail);
        recipeStepDetailFragment.showStepDetail(recipeModel.getRecipeStepList().get(0));
    }

    private void setupViewPager(RecipeModel recipeModel) {
        if (getView() == null) {
            Timber.wtf("Why is the view null?");
            return;
        }

        ViewPager viewPager = ButterKnife.findById(getView(), R.id.vpRecipeDetail);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), recipeModel, getActivity()));

        TabLayout tabLayout = ButterKnife.findById(getView(), R.id.tlRecipeDetail);
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
