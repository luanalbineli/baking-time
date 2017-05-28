package com.albineli.udacity.popularmovies;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.albineli.udacity.popularmovies.enums.MovieListFilterDescriptor;
import com.albineli.udacity.popularmovies.movielist.MovieListFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static final String SELECTED_TAB_BUNDLE_KEY = "selected_tab";

    @BindView(R.id.bb_bottom_menu)
    BottomBar mBottomBar;

    private
    @IdRes
    int mSelectedTabId = R.id.tab_popular;

    private MovieListFragment mMovieListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_TAB_BUNDLE_KEY)) {
            mSelectedTabId = savedInstanceState.getInt(SELECTED_TAB_BUNDLE_KEY);
        }

        mBottomBar.selectTabWithId(mSelectedTabId);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Timber.i("Selected item: " + tabId);
                if (tabId == R.id.tab_popular) {

                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
            }
        });

        setupMovieListFragment();

        getFragmentManager().addOnBackStackChangedListener(this);

        checkShouldDisplayBackButton();
    }

    private void setupMovieListFragment() {
        final String movieListFragmentTag = "movie_list";
        FragmentManager fragmentManager = getFragmentManager();
        mMovieListFragment = (MovieListFragment) fragmentManager.findFragmentByTag(movieListFragmentTag);
        if (mMovieListFragment == null) {
            @MovieListFilterDescriptor.MovieListFilter int filter = getFilterBySelectedTab(mSelectedTabId);

            mMovieListFragment = MovieListFragment.getInstance(filter);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fl_main_content, mMovieListFragment, movieListFragmentTag)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .commit();
        }
    }

    private void checkShouldDisplayBackButton() {
        boolean shouldDisplayBackButton = getFragmentManager().getBackStackEntryCount() > 0;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(shouldDisplayBackButton);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        checkShouldDisplayBackButton();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECTED_TAB_BUNDLE_KEY, mSelectedTabId);
    }

    private @MovieListFilterDescriptor.MovieListFilter int getFilterBySelectedTab(@IdRes int selectedTabId) {
        switch (selectedTabId) {
            case R.id.tab_popular:
                return MovieListFilterDescriptor.POPULAR;
            case R.id.tab_rating:
                return MovieListFilterDescriptor.RATING;
            default:
                return MovieListFilterDescriptor.FAVORITE;
        }
    }
}
