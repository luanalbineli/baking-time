package com.albineli.udacity.popularmovies.repository;


import com.albineli.udacity.popularmovies.model.RecipeModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RecipeRepository extends RepositoryBase {
    private static IRecipeService mMovieService;
    private final Retrofit mRetrofit;

    private IRecipeService getRecipeServiceInstance() {
        if (mMovieService == null) {
            mMovieService = mRetrofit.create(IRecipeService.class);
        }
        return mMovieService;
    }

    @Inject
    RecipeRepository(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public Observable<List<RecipeModel>> getRecipeList() {
        return observeOnMainThread(getRecipeServiceInstance().getRecipeList());
    }
}
