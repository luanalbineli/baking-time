package com.albineli.udacity.popularmovies.util;

public abstract class ApiUtil {
    private static final String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";
    private static final int[] POSTER_SIZE = {92, 154, 185, 342, 500, 780}; // TODO: Hardcoded, we should call /configuration.

    public static String buildPosterImageUrl(String posterKey, String posterWidth) {
        return BASE_URL_POSTER + posterWidth + "/" + posterKey;
    }

    public static String getDefaultPosterSize(int widthPx) {
        if (widthPx > POSTER_SIZE[POSTER_SIZE.length - 1]) {
            return "original";
        }
        for (int size: POSTER_SIZE) {
            if (size > widthPx) {
                return "w" + size;
            }
        }
        return "original";
    }
}
