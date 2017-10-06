package com.udacity.bakingtime.recipedetail.ingredientlistdialog;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.injector.components.ApplicationComponent;
import com.udacity.bakingtime.injector.components.DaggerFragmentComponent;
import com.udacity.bakingtime.model.RecipeIngredientModel;
import com.udacity.bakingtime.recipedetail.ingredientlist.RecipeIngredientListFragment;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class IngredientListDialog extends DialogFragment {
    private List<RecipeIngredientModel> mRecipeIngredientList;
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(INGREDIENT_LIST_BUNDLE_KEY)) {
            throw new InvalidParameterException(INGREDIENT_LIST_BUNDLE_KEY);
        }

        mRecipeIngredientList = getArguments().getParcelableArrayList(INGREDIENT_LIST_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ingredient_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment fragment = getChildFragmentManager().findFragmentByTag(INGREDIENT_LIST_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = RecipeIngredientListFragment.getInstance(mRecipeIngredientList);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.flIngredientListDialogContainer, fragment, INGREDIENT_LIST_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public static IngredientListDialog getInstance(List<RecipeIngredientModel> recipeIngredientList) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(INGREDIENT_LIST_BUNDLE_KEY, new ArrayList<>(recipeIngredientList));

        IngredientListDialog instance = new IngredientListDialog();
        instance.setArguments(arguments);
        return instance;
    }

    private static final String INGREDIENT_LIST_FRAGMENT_TAG = "ingredient_list_fragment_tag";
    private static final String INGREDIENT_LIST_BUNDLE_KEY = "ingredient_list_bundle_key";
}

