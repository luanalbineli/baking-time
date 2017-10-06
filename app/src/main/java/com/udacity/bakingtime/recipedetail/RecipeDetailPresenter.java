package com.udacity.bakingtime.recipedetail;


import android.support.annotation.NonNull;


import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.event.SelectStepEvent;
import com.udacity.bakingtime.model.RecipeDetailViewModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeDetailPresenter extends BasePresenterImpl implements RecipeDetailContract.Presenter {
    private RecipeDetailContract.View mView;

    RecipeDetailViewModel recipeDetailViewModel;

    @Inject
    RecipeDetailPresenter(@NonNull RecipeRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public void setView(RecipeDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start(RecipeDetailViewModel recipeDetailViewModel) {
        this.recipeDetailViewModel = recipeDetailViewModel;
        if (!recipeDetailViewModel.useMasterDetail) {
            mView.configureNormalLayout(recipeDetailViewModel.recipeModel);
        }

        if (recipeDetailViewModel.selectedStepIndex == null) {
            recipeDetailViewModel.selectedStepIndex = 0;
        }

        mView.configureMasterDetail(recipeDetailViewModel.recipeModel, recipeDetailViewModel.selectedStepIndex, recipeDetailViewModel.recipeModel.getRecipeStepList().get(recipeDetailViewModel.selectedStepIndex));

        if (recipeDetailViewModel.ingredientListDialogDismissed == null || !recipeDetailViewModel.ingredientListDialogDismissed) {
            recipeDetailViewModel.ingredientListDialogDismissed = false;
            mView.showIngredientList(recipeDetailViewModel.recipeModel.getIngredientList());
        }
    }

    @Override
    public void showIngredientList() {
        mView.showIngredientList(recipeDetailViewModel.recipeModel.getIngredientList());
    }

    @Override
    public void onDismissIngredientList() {
        recipeDetailViewModel.ingredientListDialogDismissed = true;
    }

    @Override
    public void onSelectStepEvent(SelectStepEvent selectStepEvent) {
        recipeDetailViewModel.selectedStepIndex = selectStepEvent.selectedIndex;
        mView.showStepDetail(selectStepEvent.recipeStepModel);
    }
}
