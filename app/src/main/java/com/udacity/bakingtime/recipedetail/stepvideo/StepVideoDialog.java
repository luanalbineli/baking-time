package com.udacity.bakingtime.recipedetail.stepvideo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.udacity.bakingtime.R;
import com.udacity.bakingtime.exoplayer.ExoPlayerFragment;
import com.udacity.bakingtime.util.UIUtil;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepVideoDialog extends DialogFragment {
    @BindView(R.id.toolbarStepVideoDialog)
    Toolbar mDialogToolbar;

    private String mVideoUrl;
    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(VIDEO_URL_BUNDLE) || !getArguments().containsKey(TITLE_BUNDLE)) {
            throw new InvalidParameterException("Missing some required parameters");
        }

        mVideoUrl = getArguments().getString(VIDEO_URL_BUNDLE);
        mTitle = getArguments().getString(TITLE_BUNDLE);
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
        View view = inflater.inflate(R.layout.step_video_dialog, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Drawable drawable = getResources().getDrawable(R.drawable.arrow_left);
        UIUtil.paintDrawable(drawable, getResources().getColor(android.R.color.white));
        mDialogToolbar.setNavigationIcon(drawable);
        mDialogToolbar.setNavigationOnClickListener(v -> dismiss());
        mDialogToolbar.setTitle(mTitle);

        String EXO_PLAYER_FRAGMENT_TAG = "exo_player_fragment_tag";

        Fragment fragment = getChildFragmentManager().findFragmentByTag(EXO_PLAYER_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = ExoPlayerFragment.getInstance(mVideoUrl);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.flStepVideoContainer, fragment, EXO_PLAYER_FRAGMENT_TAG)
                    .commit();
        }
    }

    public static StepVideoDialog getInstance(String title, String videoUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_BUNDLE, title);
        bundle.putString(VIDEO_URL_BUNDLE, videoUrl);

        StepVideoDialog instance = new StepVideoDialog();
        instance.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_DialogFullscreen);
        instance.setArguments(bundle);

        return instance;
    }

    private static String TITLE_BUNDLE = "title_bundle";
    private static String VIDEO_URL_BUNDLE = "video_url_bundle";
}
