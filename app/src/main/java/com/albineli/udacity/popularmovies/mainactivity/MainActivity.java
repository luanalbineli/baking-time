package com.albineli.udacity.popularmovies.mainactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.recipelist.RecipeListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);

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
        mToobarTitle.setText(getString(titleResId));
    }
}
