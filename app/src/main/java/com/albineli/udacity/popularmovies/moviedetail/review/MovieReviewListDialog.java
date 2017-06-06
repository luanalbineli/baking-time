package com.albineli.udacity.popularmovies.moviedetail.review;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MovieReviewListDialog extends DialogFragment {
    private static final String MOVIE_REVIEW_LIST_BUNDLE_KEY = "movie_review_list";
    private ArrayList<MovieReviewModel> mReviewList;

    public static MovieReviewListDialog getInstance(List<MovieReviewModel> movieModelList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_REVIEW_LIST_BUNDLE_KEY, new ArrayList<>(movieModelList));

        MovieReviewListDialog filterDialogFragment = new MovieReviewListDialog();
        filterDialogFragment.setArguments(bundle);
        filterDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_DialogFullscreen);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(MOVIE_REVIEW_LIST_BUNDLE_KEY)) {
            throw new InvalidParameterException("movie");
        }

        mReviewList = getArguments().getParcelableArrayList(MOVIE_REVIEW_LIST_BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_review_list_dialog, container);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


}
