package com.albineli.udacity.popularmovies.repository;


import com.albineli.udacity.popularmovies.PopularMovieApplication;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class RecipeRepository extends RepositoryBase {
    private static IRecipeService mMovieService;
    /*private static String MOVIE_LIST_FILTER_KEY = "movie_list_filter";
    private static final String SP_KEY = "sp_popular_movies";*/

    private final Retrofit mRetrofit;
    //private final SharedPreferences mSharedPreferences;
    private final PopularMovieApplication mApplicationContext;

    @Inject
    RecipeRepository(Retrofit retrofit, PopularMovieApplication applicationContext) {
        mRetrofit = retrofit;
        mApplicationContext = applicationContext;
    }


}
