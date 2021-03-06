package com.udacity.bakingtime.exoplayer;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.bakingtime.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExoPlayerFragment extends Fragment implements ExtractorMediaSource.EventListener {
    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.flPlayerThumbnailContainer)
    FrameLayout mThumbnailContainer;

    @BindView(R.id.ivPlayerThumbnailImage)
    ImageView mThumbnailImageView;

    @BindView(R.id.ivPlayerThumbnailPlay)
    ImageView mThumbnailPlayImageView;

    SimpleExoPlayer mPlayer;

    private String mVideoUrl;
    private String mThumbnailUrl;
    private long mCurrentPlayerPosition = Long.MIN_VALUE;
    private boolean mStarted = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(VIDEO_URL_BUNDLE_KEY)) {
                mVideoUrl = getArguments().getString(VIDEO_URL_BUNDLE_KEY);
            }

            if (getArguments().containsKey(THUMBNAIL_URL_BUNDLE_KEY)) {
                mThumbnailUrl = getArguments().getString(THUMBNAIL_URL_BUNDLE_KEY);
            }
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_VIDEO_POSITION_BUNDLE_KEY)) {
            mCurrentPlayerPosition = savedInstanceState.getLong(CURRENT_VIDEO_POSITION_BUNDLE_KEY, Long.MIN_VALUE);
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
    public void onStart() {
        super.onStart();
        mStarted = true;
        if (TextUtils.isEmpty(mVideoUrl)) {
            return;
        }

        configureVideoPlayer();
    }

    private Target mThumbnailTarget;

    private void configureThumbnail() {
        if (mThumbnailTarget == null) {
            mThumbnailTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    mThumbnailImageView.setImageBitmap(bitmap);
                    Timber.i("configureThumbnail() - An successfully loaded.");
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {
                    mThumbnailContainer.setVisibility(View.GONE);
                    Timber.i("configureThumbnail() - An error occurred while tried to load the thumbnail");
                }

                @Override
                public void onPrepareLoad(Drawable drawable) {
                    mThumbnailImageView.setImageDrawable(drawable);
                    Timber.i("configureThumbnail() - Started the download of the thumbnail.");
                }
            };
        }

        Timber.i("configureThumbnail() - URL: " + mThumbnailUrl);
        if (TextUtils.isEmpty(mThumbnailUrl)) {
            mThumbnailContainer.setVisibility(View.GONE);
            mPlayer.setPlayWhenReady(true);
            mThumbnailPlayImageView.setOnClickListener(null);
        } else {
            mThumbnailContainer.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(mThumbnailUrl)
                    .placeholder(R.drawable.loading_image_placeholder)
                    .into(mThumbnailTarget);

            mThumbnailPlayImageView.setOnClickListener(v -> {
                mThumbnailContainer.setVisibility(View.GONE);
                mPlayer.setPlayWhenReady(true);
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mCurrentPlayerPosition = Long.MIN_VALUE;
        if (mPlayer != null) {
            mCurrentPlayerPosition = mPlayer.getCurrentPosition();
        }
        releasePlayer();
    }

    public void releasePlayer() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(CURRENT_VIDEO_POSITION_BUNDLE_KEY, mCurrentPlayerPosition);
    }

    private void configureVideoPlayer() {
        if (mPlayer == null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(bandwidthMeter);

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            mPlayerView.setPlayer(mPlayer);
        }

        Uri mp4VideoUri = Uri.parse(mVideoUrl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "exoPlayerFragment"));

        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, new DefaultExtractorsFactory(), null, this);

        mPlayer.prepare(videoSource);

        if (mCurrentPlayerPosition != Long.MIN_VALUE) {
            mPlayer.seekTo(mCurrentPlayerPosition);
        }

        configureThumbnail();
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

    public void showVideo(String videoUrl, String thumbnailUrl) {
        this.mVideoUrl = videoUrl;
        this.mThumbnailUrl = thumbnailUrl;

        if (mStarted && !TextUtils.isEmpty(mVideoUrl)) {
            configureVideoPlayer();
        }
    }

    public static ExoPlayerFragment getInstance(String videoUrl, String thumbnailUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL_BUNDLE_KEY, videoUrl);
        bundle.putString(THUMBNAIL_URL_BUNDLE_KEY, thumbnailUrl);

        ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
        exoPlayerFragment.setArguments(bundle);

        return exoPlayerFragment;
    }

    private static String VIDEO_URL_BUNDLE_KEY = "video_url";
    private static String CURRENT_VIDEO_POSITION_BUNDLE_KEY = "current_video_position";
    private static String THUMBNAIL_URL_BUNDLE_KEY = "thumbnail_url";
}
