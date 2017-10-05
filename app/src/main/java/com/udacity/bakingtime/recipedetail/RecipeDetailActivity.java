package com.udacity.bakingtime.recipedetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseActivity;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.udacity.bakingtime.recipedetail.ingredientlistdialog.IngredientListDialog;
import com.udacity.bakingtime.recipedetail.stepdetail.RecipeStepDetailFragment;
import com.udacity.bakingtime.recipedetail.steplist.RecipeStepListFragment;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class RecipeDetailActivity extends BaseActivity<RecipeDetailContract.View> implements RecipeDetailContract.View {
    @Override
    protected BasePresenter<RecipeDetailContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected RecipeDetailContract.View getViewImplementation() {
        return this;
    }

    @Inject
    RecipeDetailPresenter mPresenter;

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
        if (getIntent() == null || !getIntent().hasExtra(RECIPE_MODEL_BUNDLE_KEY)) {
            throw new InvalidParameterException("We need the recipe detail to open pal :(");
        }

        setContentView(R.layout.fragment_recipe_detail);

        RecipeModel recipeModel = getIntent().getParcelableExtra(RECIPE_MODEL_BUNDLE_KEY);

        setTitle(recipeModel.getName());

        mPresenter.start(recipeModel);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean useMasterDetail = getResources().getBoolean(R.bool.useMasterDetail);
        if (useMasterDetail) {
            getMenuInflater().inflate(R.menu.recipe_detail_tablet_menu, menu);
        }

        return useMasterDetail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_show_ingredient_list:
                mPresenter.showIngredientList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRecipeDetailContent(RecipeModel recipeModel) {
        boolean useMasterDetail = getResources().getBoolean(R.bool.useMasterDetail);
        if (useMasterDetail) {
            configureMasterDetailLayout(recipeModel);
        } else {
            configureDefaultLayout(recipeModel);
        }
    }

    @Override
    public void showIngredientList(List<RecipeIngredientModel> ingredientList) {
        IngredientListDialog ingredientListDialog = IngredientListDialog.getInstance(ingredientList);
        ingredientListDialog.show(getFragmentManager(), "ingredient_list_dialog");
    }

    private void configureDefaultLayout(RecipeModel recipeModel) {
        if (!TextUtils.isEmpty(recipeModel.getImage())) {
            // TODO: implementation to fetch the image.
            ImageView ingredientImage = ButterKnife.findById(this, R.id.ivRecipeItemPicture);
        }
        setupViewPager(recipeModel);
    }

    private void configureMasterDetailLayout(RecipeModel recipeModel) {
        RecipeStepListFragment recipeStepListFragment = (RecipeStepListFragment) getFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepList);
        recipeStepListFragment.setStepList(recipeModel.getRecipeStepList());

        RecipeStepDetailFragment recipeStepDetailFragment = (RecipeStepDetailFragment) getFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepDetail);
        recipeStepDetailFragment.showStepDetail(recipeModel.getRecipeStepList().get(0));
    }

    private void setupViewPager(RecipeModel recipeModel) {
        ViewPager viewPager = ButterKnife.findById(this, R.id.vpRecipeDetail);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager(), recipeModel, this));

        TabLayout tabLayout = ButterKnife.findById(this, R.id.tlRecipeDetail);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static String RECIPE_MODEL_BUNDLE_KEY = "recipe_model";
    public static int VIEW_RECIPE_DETAIL_REQUEST_CODE = 1001;
    public static String VIEW_RECIPE_DETAIL_ACTION = "view_recipe_detail";

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
