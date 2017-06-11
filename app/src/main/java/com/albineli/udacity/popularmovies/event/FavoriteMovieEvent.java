package com.albineli.udacity.popularmovies.event;

import com.albineli.udacity.popularmovies.model.MovieModel;


public class FavoriteMovieEvent {
    public final MovieModel movie;
    public final boolean favorite;

    public FavoriteMovieEvent(MovieModel movie, boolean favorite) {
        this.movie = movie;
        this.favorite = favorite;
    }
}
