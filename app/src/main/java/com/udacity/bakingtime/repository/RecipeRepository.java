package com.udacity.bakingtime.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.model.RecipeModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RecipeRepository extends RepositoryBase {
    private static final String INTERNAL_SP = "internal_shared_preferences";
    private static final String RECIPE_LIST_SP_KEY = "internal_cached_recipe_list";
    private static final String RECIPE_ID_TO_WIDGET_ID_MAP_SP_KEY = "internal_recipe_id_to_widget_id_map";

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

    public RecipeRepository(Retrofit retrofit, BakingTimeApplication bakingTimeApplication, Gson gson) {
        mRetrofit = retrofit;
        mInternalSP = bakingTimeApplication.getSharedPreferences(INTERNAL_SP, Context.MODE_PRIVATE);
        mGson = gson;
    }

    public Observable<List<RecipeModel>> getRecipeList() {
        Observable<List<RecipeModel>> request = getRecipeServiceInstance().getRecipeList();
        return observeOnMainThread(request)/*.map(recipeModels -> {
            recipeModels.get(0).setImage("https://github.com/bumptech/glide/blob/master/static/glide_logo.png?raw=true");
            recipeModels.get(0).getRecipeStepList().get(0).setThumbnailUrl("http://api.ning.com/files/3UVmg*XKC3SyPMca*be0YYFj3FnE7TmfB112M1nM*NnMeE5-60ItOGGPMDg5kwo4E4DUGhWh8gO2soPf1z1OtLmniPm7Yv2N/Dadafrutaespecial.png");
            return recipeModels;
        })*/;
    }

    public void cacheRecipeList(@NotNull List<RecipeModel> recipeList) {
        mInternalSP.edit()
                .putString(RECIPE_LIST_SP_KEY, mGson.toJson(recipeList))
                .apply();
    }

    public @Nullable
    List<RecipeModel> getCachedRecipeList() {
        if (!mInternalSP.contains(RECIPE_LIST_SP_KEY)) {
            return null;
        }

        String rawRecipeList = mInternalSP.getString(RECIPE_LIST_SP_KEY, "");
        if (TextUtils.isEmpty(rawRecipeList)) {
            return null;
        }
        return mGson.fromJson(rawRecipeList, new TypeToken<List<RecipeModel>>() {
        }.getType());
    }

    public @Nullable
    RecipeModel getCachedRecipeByWidgetId(int widgetId) {
        Timber.i("Getting the recipe by widget ID: " + widgetId);
        List<RecipeModel> recipeList = getCachedRecipeList();
        if (recipeList == null) {
            return null;
        }

        SparseIntArray sparseIntArray = getRecipeIdToWidgetIdMap();
        Timber.i("Map with widgetId/recipe values: " + sparseIntArray);
        int recipeId = sparseIntArray.get(widgetId);
        if (recipeId < 0) {
            return null;
        }
        RecipeModel result = null;
        for (RecipeModel recipeModel : recipeList) {
            if (recipeModel.getId() == recipeId) {
                result = recipeModel;
                break;
            }
        }
        Timber.i("Result of the search: " + result);
        return result;
    }

    public void saveRecipeIdToWidgedId(int widgetId, int recipeId) {
        SparseIntArray sparseIntArray = getRecipeIdToWidgetIdMap();
        sparseIntArray.put(widgetId, recipeId);
        saveRecipeIdToWidgedIdSparseArray(sparseIntArray);
    }

    private SparseIntArray mRecipeIdToWidgetIdSparseArray;

    private SparseIntArray getRecipeIdToWidgetIdMap() {
        if (mRecipeIdToWidgetIdSparseArray != null) {
            return mRecipeIdToWidgetIdSparseArray;
        }

        if (mInternalSP.contains(RECIPE_ID_TO_WIDGET_ID_MAP_SP_KEY)) {
            String rawSparseIntArray = mInternalSP.getString(RECIPE_ID_TO_WIDGET_ID_MAP_SP_KEY, "");
            if (!TextUtils.isEmpty(rawSparseIntArray)) {
                mRecipeIdToWidgetIdSparseArray = mGson.fromJson(rawSparseIntArray, SparseIntArray.class);

                return mRecipeIdToWidgetIdSparseArray;
            }
        }

        return mRecipeIdToWidgetIdSparseArray = new SparseIntArray();
    }

    private void saveRecipeIdToWidgedIdSparseArray(SparseIntArray sparseIntArray) {
        String jsonedSparseArray = mGson.toJson(sparseIntArray);
        Timber.i("Saving the widget/recipe map: " + jsonedSparseArray);
        mInternalSP.edit()
                .putString(RECIPE_ID_TO_WIDGET_ID_MAP_SP_KEY, jsonedSparseArray)
                .apply();
    }

    public void removeRecipeIdByWidgetId(int widgetId) {
        SparseIntArray sparseIntArray = getRecipeIdToWidgetIdMap();
        sparseIntArray.delete(widgetId);
        saveRecipeIdToWidgedIdSparseArray(sparseIntArray);
    }
}
