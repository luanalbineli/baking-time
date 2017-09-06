package com.udacity.bakingtime.repository;


import com.udacity.bakingtime.model.RecipeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IRecipeService {
    @GET("android-baking-app-json")
    Observable<List<RecipeModel>> getRecipeList();
}
