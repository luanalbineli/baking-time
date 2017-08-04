package com.albineli.udacity.popularmovies.event;

import com.albineli.udacity.popularmovies.model.RecipeModel;


public class FavoriteMovieEvent {
    public final RecipeModel movie;
    public final boolean favorite;

    public FavoriteMovieEvent(RecipeModel movie, boolean favorite) {
        this.movie = movie;
        this.favorite = favorite;
    }
}
