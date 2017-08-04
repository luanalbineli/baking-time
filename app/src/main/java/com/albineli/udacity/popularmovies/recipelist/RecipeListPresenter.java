package com.albineli.udacity.popularmovies.recipelist;

import android.support.annotation.NonNull;

import com.albineli.udacity.popularmovies.base.BasePresenterImpl;
import com.albineli.udacity.popularmovies.model.RecipeModel;
import com.albineli.udacity.popularmovies.repository.RecipeRepository;

import javax.inject.Inject;

/**
 * Presenter of the recipe list fragment.
 */

public class RecipeListPresenter extends BasePresenterImpl implements RecipeListContract.Presenter {
    private RecipeListContract.View mView;

    @Inject
    RecipeListPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
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
                        recipeList -> mView.showRecipeList(recipeList),
                        error -> mView.showErrorLoadingRecipeList(error));
    }

    @Override
    public void openRecipeDetail(int position, RecipeModel recipeModel) {

    }
}
