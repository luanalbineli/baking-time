package com.udacity.bakingtime.exoplayer;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingtime.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExoPlayerFragment extends Fragment implements ExtractorMediaSource.EventListener {
    private static String VIDEO_URL_BUNDLE_KEY = "video_url";
    private long mCurrentPlayerPosition = Long.MIN_VALUE;

    public static ExoPlayerFragment getInstance(String videoUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL_BUNDLE_KEY, videoUrl);

        ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
        exoPlayerFragment.setArguments(bundle);

        return exoPlayerFragment;
    }

    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    SimpleExoPlayer mPlayer;

    private String mVideoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(VIDEO_URL_BUNDLE_KEY)) {
            mVideoUrl = getArguments().getString(VIDEO_URL_BUNDLE_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exoplayer_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configurePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mVideoUrl != null) {
            playVideo();
        }

        if (mCurrentPlayerPosition != Long.MIN_VALUE) {
            mPlayer.seekTo(mCurrentPlayerPosition);
        }
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlayer.stop();
        mPlayer.release();

        mCurrentPlayerPosition = mPlayer.getCurrentPosition();
    }

    private void configurePlayer() {
        BandwidthMeter bandwidthMeter = null;
        TrackSelector trackSelector = new DefaultTrackSelector(bandwidthMeter);

        mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        mPlayerView.setPlayer(mPlayer);
    }

    private void playVideo() {
        Uri mp4VideoUri = Uri.parse(mVideoUrl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "exoPlayerFragment"));

        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, new DefaultExtractorsFactory(), null, this);

        mPlayer.prepare(videoSource);
    }

    @Override
    public void onLoadError(IOException error) {
        Timber.e(error, "An error occurred while tried to load the video");
    }

    public void setVisibility(int visible) {
        if (getView() != null) {
            getView().setVisibility(visible);
        }
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
        if (mVideoUrl != null) {
            playVideo();
        }
    }
}
