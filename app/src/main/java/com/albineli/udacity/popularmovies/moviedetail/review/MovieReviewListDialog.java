package com.albineli.udacity.popularmovies.moviedetail.review;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;

public class MovieReviewListDialog extends DialogFragment {

    public static MovieReviewListDialog getInstance() {
        MovieReviewListDialog filterDialogFragment = new MovieReviewListDialog();
        filterDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_NoActionBar);
        return filterDialogFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_review_list_dialog, container);
    }

    @Override
    public void onResume() {
        super.onResume();


    }


}
