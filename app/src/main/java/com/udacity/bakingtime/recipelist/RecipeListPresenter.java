package com.udacity.bakingtime.recipelist;

import android.support.annotation.NonNull;


import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

/**
 * Presenter of the recipe list fragment.
 */

public class RecipeListPresenter extends BasePresenterImpl implements RecipeListContract.Presenter {
    private RecipeListContract.View mView;

    @Inject
    RecipeListPresenter(@NonNull RecipeRepository recipeRepository) {
        super(recipeRepository);
    }

    @Override
    public void setView(RecipeListContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mRecipeRepository.getRecipeList()
                .doOnSubscribe(disposable -> mView.showLoadingIndicator())
                .doOnTerminate(mView::hideLoadingIndicator)
                .subscribe(
                        recipeList -> {
                            mView.showRecipeList(recipeList);
                            mRecipeRepository.cacheRecipeList(recipeList);

                        },
                        error -> mView.showErrorLoadingRecipeList(error));
    }

    @Override
    public void handleSelectRecipeEvent(RecipeModel recipeModel) {
        mView.goToRecipeDetail(recipeModel);
    }
}
