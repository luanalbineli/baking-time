package com.udacity.bakingtime.recipedetail.stepdetail;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.udacity.bakingtime.base.BasePresenterImpl;
import com.udacity.bakingtime.model.RecipeModel;
import com.udacity.bakingtime.model.RecipeStepModel;
import com.udacity.bakingtime.repository.RecipeRepository;

import javax.inject.Inject;

public class RecipeStepDetailPresenter extends BasePresenterImpl implements RecipeStepDetailContract.Presenter {
    private RecipeStepDetailContract.View mView;

    @Inject
    RecipeStepDetailPresenter(@NonNull RecipeRepository recipeRepository) {
        super(recipeRepository);
    }

    @Override
    public void setView(RecipeStepDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start(@Nullable RecipeModel recipeModel) {
    }

    @Override
    public void onSelectStep(RecipeStepModel recipeStepModel) {
        mView.showStepDetail(recipeStepModel);
    }
}
