/*
 * Copyright (C) 2013 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.albineli.udacity.popularmovies.injector.modules;

import com.albineli.udacity.popularmovies.injector.PerFragment;
import com.albineli.udacity.popularmovies.movielist.MovieListContract;
import com.albineli.udacity.popularmovies.movielist.MovieListFragment;
import com.albineli.udacity.popularmovies.movielist.MovieListPresenter;
import com.albineli.udacity.popularmovies.repository.movie.MovieRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieListModule {
    private final MovieListFragment mMovieListFragment;

    public MovieListModule(MovieListFragment movieDetailFragment) {
        this.mMovieListFragment = movieDetailFragment;
    }

    /**
     * Expose the mMovieListFragment to dependents in the graph.
     */
    @Provides
    @PerFragment
    MovieListFragment fragment() {
        return mMovieListFragment;
    }

    @Provides
    @PerFragment
    MovieListContract.Presenter presenter(MovieRepository movieRepository) {
        return new MovieListPresenter(mMovieListFragment, movieRepository);
    }
}