package com.albineli.udacity.popularmovies.base;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.PopularMovieApplication;
import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.util.UIUtil;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public abstract class BaseFullscreenDialogWithList<TModel extends Parcelable, TView> extends DialogFragment {
    private static final String LIST_BUNDLE_KEY = "list";

    @BindView(R.id.rvFullscreenFragmentDialog)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.toolbarMovieReviewDialog)
    Toolbar mDialogToolbar;

    protected ArrayList<TModel> mList;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(LIST_BUNDLE_KEY)) {
            throw new InvalidParameterException(LIST_BUNDLE_KEY);
        }

        mList = getArguments().getParcelableArrayList(LIST_BUNDLE_KEY);

        ApplicationComponent applicationComponent = PopularMovieApplication.getApplicationComponent(getActivity());
        onInjectDependencies(applicationComponent);

        getPresenterImplementation().setView(getViewImplementation());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fullscreen_fragment_dialog_with_list, container);

        ButterKnife.bind(this, rootView);

        Drawable drawable = getResources().getDrawable(R.drawable.arrow_left);
        UIUtil.paintDrawable(drawable, getResources().getColor(android.R.color.white));
        mDialogToolbar.setNavigationIcon(drawable);
        mDialogToolbar.setNavigationOnClickListener(v -> dismiss());

        return rootView;
    }

    protected static <TFragmentDialog extends BaseFullscreenDialogWithList<TModel, ?>, TModel extends Parcelable> TFragmentDialog createNewInstance(Class<TFragmentDialog> clazz, List<TModel> items) {
        try {
            TFragmentDialog baseFullscreenDialogWithList = clazz.newInstance();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LIST_BUNDLE_KEY, new ArrayList<>(items));
            baseFullscreenDialogWithList.setArguments(bundle);
            baseFullscreenDialogWithList.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_DialogFullscreen);

            return baseFullscreenDialogWithList;
        } catch (Exception e) {
            Timber.e(e, "An error occurred while tried to instantiate a new fullscreen dialog");
        }
        return null;
    }

    protected abstract void onInjectDependencies(ApplicationComponent applicationComponent);

    protected abstract BasePresenter<TView> getPresenterImplementation();
    protected abstract TView getViewImplementation();
}
