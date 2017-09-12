package com.udacity.bakingtime.mainactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.event.SelectRecipeEvent;
import com.udacity.bakingtime.event.SelectStepEvent;
import com.udacity.bakingtime.recipelist.RecipeListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;

    public static int VIEW_RECIPE_DETAIL_REQUEST_CODE = 1001;
    public static String VIEW_RECIPE_DETAIL_ACTION = "view_recipe_detail";
    public static String RECIPE_BUNDLE_KEY = "recipe_model";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        super.setTitle(null);
        Timber.i("ACTION: " + getIntent().getAction());
        ButterKnife.bind(this);
        setupMovieListFragment();

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        checkShouldDisplayBackButton();
    }

    private void setupMovieListFragment() {
        final String fragmentTag = "recipe_list";
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = (RecipeListFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (recipeListFragment == null) {
            recipeListFragment = RecipeListFragment.getInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fl_main_content, recipeListFragment, fragmentTag)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .commit();
        }
    }

    private void checkShouldDisplayBackButton() {
        boolean shouldDisplayBackButton = getSupportFragmentManager().getBackStackEntryCount() > 0;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(shouldDisplayBackButton);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        checkShouldDisplayBackButton();
    }

    @Override
    public void setTitle(int titleResId) {
        setTitle(getString(titleResId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mToobarTitle.setText(title);
    }
}
