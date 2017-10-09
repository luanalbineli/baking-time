package com.udacity.bakingtime.recipedetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.udacity.bakingtime.R;
import com.udacity.bakingtime.base.BaseActivity;
import com.udacity.bakingtime.base.BasePresenter;
import com.udacity.bakingtime.event.SelectStepEvent;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeDetailViewModel;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.model.RecipeStepModel;
import com.udacity.bakingtime.recipedetail.ingredientlist.RecipeIngredientListFragment;
import com.udacity.bakingtime.recipedetail.ingredientlistdialog.IngredientListDialog;
import com.udacity.bakingtime.recipedetail.stepdetail.RecipeStepDetailFragment;
import com.udacity.bakingtime.recipedetail.steplist.RecipeStepListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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

    @BindView(R.id.ivRecipeDetailImage)
    ImageView mRecipeImageView;

    RecipeStepDetailFragment mRecipeStepDetailFragment;

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

        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecipeDetailViewModel recipeDetailViewModel = buildRecipeDetailViewModel(getIntent(), savedInstanceState);
        mPresenter.start(recipeDetailViewModel);
    }

    private RecipeDetailViewModel buildRecipeDetailViewModel(Intent intent, Bundle savedInstanceState) {
        RecipeModel recipeModel = intent.getParcelableExtra(RECIPE_MODEL_BUNDLE_KEY);
        Integer selectedStepIndex = null;
        Boolean ingredientListShowing = null;
        if (savedInstanceState != null) {
            selectedStepIndex = savedInstanceState.containsKey(SELECTED_STEP_INDEX_BUNDLE_KEY) ? savedInstanceState.getInt(SELECTED_STEP_INDEX_BUNDLE_KEY) : null;
            ingredientListShowing = savedInstanceState.containsKey(INGREDIENT_LIST_DISMISSED_BUNDLE_KEY) && savedInstanceState.getBoolean(INGREDIENT_LIST_DISMISSED_BUNDLE_KEY);
        }

        return new RecipeDetailViewModel(recipeModel,
                selectedStepIndex,
                ingredientListShowing,
                getResources().getBoolean(R.bool.useMasterDetail));
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    public void showIngredientList(List<RecipeIngredientModel> ingredientList) {
        final String ingredientListDialogTag = "ingredient_list_dialog";

        IngredientListDialog ingredientListDialog;
        if (getFragmentManager().findFragmentByTag(ingredientListDialogTag) != null) {
            ingredientListDialog = (IngredientListDialog) getFragmentManager().findFragmentByTag(ingredientListDialogTag);
        } else {
            ingredientListDialog = IngredientListDialog.getInstance(ingredientList);

            ingredientListDialog.show(getFragmentManager(), ingredientListDialogTag);
        }

        ingredientListDialog.setOnDismissListener(dialogInterface -> mPresenter.onDismissIngredientList());
    }

    @Override
    public void configureMasterDetail(RecipeModel recipeModel, int selectedStepIndex, RecipeStepModel recipeStepModel) {
        RecipeStepListFragment recipeStepListFragment = (RecipeStepListFragment) getFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepList);
        recipeStepListFragment.setStepList(recipeModel.getRecipeStepList());
        recipeStepListFragment.setSelectedRecipeStep(selectedStepIndex);

        mRecipeStepDetailFragment = (RecipeStepDetailFragment) getFragmentManager().findFragmentById(R.id.fragmentRecipeDetailStepDetail);
        mRecipeStepDetailFragment.showStepDetail(recipeStepModel);

        configureCommonLayout(recipeModel);
    }

    private void configureCommonLayout(RecipeModel recipeModel) {
        setTitle(recipeModel.getName());

        if (TextUtils.isEmpty(recipeModel.getImage())) {
            return;
        }

        Glide.with(this)
                .load(recipeModel.getImage())
                .into(mRecipeImageView);
    }

    @Override
    public void configureNormalLayout(RecipeModel recipeModel) {
        ViewPager viewPager = ButterKnife.findById(this, R.id.vpRecipeDetail);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager(), recipeModel, this));

        TabLayout tabLayout = ButterKnife.findById(this, R.id.tlRecipeDetail);
        tabLayout.setupWithViewPager(viewPager);

        configureCommonLayout(recipeModel);
    }

    @Override
    public void showStepDetail(RecipeStepModel recipeStepModel) {
        mRecipeStepDetailFragment.showStepDetail(recipeStepModel);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectStepEvent(SelectStepEvent selectStepEvent) {
        mPresenter.onSelectStepEvent(selectStepEvent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_STEP_INDEX_BUNDLE_KEY, mPresenter.recipeDetailViewModel.selectedStepIndex);
        outState.putBoolean(INGREDIENT_LIST_DISMISSED_BUNDLE_KEY, mPresenter.recipeDetailViewModel.ingredientListDialogDismissed);
    }

    public static final String RECIPE_MODEL_BUNDLE_KEY = "recipe_model";
    public static final int VIEW_RECIPE_DETAIL_REQUEST_CODE = 1001;
    public static final String VIEW_RECIPE_DETAIL_ACTION = "view_recipe_detail";
    private static final String SELECTED_STEP_INDEX_BUNDLE_KEY = "selected_step_index_bundle_key";
    private static final String INGREDIENT_LIST_DISMISSED_BUNDLE_KEY = "ingredient_list_dismissed_bundle_key";

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
