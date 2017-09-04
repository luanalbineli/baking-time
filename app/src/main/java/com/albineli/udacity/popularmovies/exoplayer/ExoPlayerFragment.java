package com.albineli.udacity.popularmovies.exoplayer;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;
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

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExoPlayerFragment extends Fragment implements ExtractorMediaSource.EventListener {
    private static String VIDEO_URL_BUNDLE_KEY = "video_url";

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

        Timber.i("Video URL: " + mVideoUrl);
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

    private void configurePlayer() {
        BandwidthMeter bandwidthMeter = null;
        //TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(bandwidthMeter);

        mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        mPlayer.setPlayWhenReady(true);

        mPlayerView.setPlayer(mPlayer);

        if (mVideoUrl != null) {
            playVideo();
        }
    }

    private void playVideo() {
        Uri mp4VideoUri = Uri.parse(mVideoUrl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "exoPlayerFragment"));

        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, new DefaultExtractorsFactory(), null, this);

        mPlayer.prepare(videoSource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
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
