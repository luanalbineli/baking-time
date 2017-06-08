package com.albineli.udacity.popularmovies.injector.components;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.moviedetail.MovieDetailFragment;
import com.albineli.udacity.popularmovies.moviedetail.review.MovieReviewListDialog;
import com.albineli.udacity.popularmovies.moviedetail.trailer.MovieTrailerListDialog;
import com.albineli.udacity.popularmovies.movielist.MovieListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class)
public interface FragmentComponent {
    void inject(MovieDetailFragment fragment);
    void inject(MovieListFragment fragment);

    void inject(MovieReviewListDialog dialogFragment);
    void inject(MovieTrailerListDialog dialogFragment);
}
