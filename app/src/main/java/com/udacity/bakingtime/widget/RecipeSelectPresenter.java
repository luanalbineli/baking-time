package com.udacity.bakingtime.widget;

import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class RecipeSelectPresenter extends BasePresenterImpl implements RecipeSelectContract.Presenter {
    private RecipeSelectContract.View mView;

    @Inject
    public RecipeSelectPresenter(RecipeRepository recipeRepository) {
        super(recipeRepository);
    }

    @Override
    public void setView(RecipeSelectContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        List<RecipeModel> cachedRecipeList = mRecipeRepository.getCachedRecipeList();
        if (cachedRecipeList != null) {
            mView.showRecipeList(cachedRecipeList);
            return;
        }

        fetchRecipeListFromServer();
    }

    @Override
    public void tryAgain() {
        fetchRecipeListFromServer();
    }

    private void fetchRecipeListFromServer() {
        mView.showLoadingIndicator();
        mRecipeRepository.getRecipeList()
                .doOnTerminate(mView::hideLoadingIndicator)
                .subscribe(recipeList -> {
                    mRecipeRepository.cacheRecipeList(recipeList);
                    mView.showRecipeList(recipeList);
                }, error -> mView.showErrorLoadingRecipeList());
    }

    @Override
    public void handleRecipeSelection(int widgetId, RecipeModel recipeModel) {
        mRecipeRepository.saveRecipeIdToWidgedId(widgetId, recipeModel.getId());
        mView.bindWidgetView(recipeModel);
        mView.endProcess();
    }
}
