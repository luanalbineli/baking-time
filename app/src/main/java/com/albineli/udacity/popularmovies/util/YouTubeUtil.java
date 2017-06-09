package com.albineli.udacity.popularmovies.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public abstract class YouTubeUtil {
    private static final String VIDEO_URL_OPEN = "https://www.youtube.com/watch?v=%1$s";

    // TODO: Try to open the YouTube app first.
    public static void openYouTubeVideo(Context context, String videoKey) {
        Uri uri = Uri.parse(String.format(VIDEO_URL_OPEN, videoKey));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
