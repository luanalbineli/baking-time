package com.udacity.bakingtime.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.model.RecipeModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RecipeRepository extends RepositoryBase {
    private static final String INTERNAL_SP = "internal_shared_preferences";
    private static final String RECIPE_LIST_SP_KEY = "internal_shared_preferences";
    private static IRecipeService mRecipeService;
    private final Retrofit mRetrofit;
    private final SharedPreferences mInternalSP;
    private final Gson mGson;

    private IRecipeService getRecipeServiceInstance() {
        if (mRecipeService == null) {
            mRecipeService = mRetrofit.create(IRecipeService.class);
        }
        return mRecipeService;
    }

    @Inject
    RecipeRepository(Retrofit retrofit, BakingTimeApplication bakingTimeApplication, Gson gson) {
        mRetrofit = retrofit;
        mInternalSP = bakingTimeApplication.getSharedPreferences(INTERNAL_SP, Context.MODE_PRIVATE);
        mGson = gson;
    }

    public Observable<List<RecipeModel>> getRecipeList() {
        return observeOnMainThread(getRecipeServiceInstance().getRecipeList());
    }

    public void cacheRecipeList(@NotNull List<RecipeModel> recipeList) {
        mInternalSP.edit()
                .putString(RECIPE_LIST_SP_KEY, mGson.toJson(recipeList))
                .apply();
    }

    public @Nullable List<RecipeModel> getCachedRecipeList() {
        if (!mInternalSP.contains(RECIPE_LIST_SP_KEY)) {
            return null;
        }

        String rawRecipeList = mInternalSP.getString(RECIPE_LIST_SP_KEY, "");
        if (TextUtils.isEmpty(rawRecipeList)) {
            return null;
        }
        return mGson.fromJson(rawRecipeList, new TypeToken<List<RecipeModel>>(){}.getType());
    }
}
