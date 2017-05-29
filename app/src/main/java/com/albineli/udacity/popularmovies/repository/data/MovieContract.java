package com.albineli.udacity.popularmovies.repository.data;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.albineli.udacity.popuparmovies.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_POSTER_PATH = "posterPath";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_RELEASE_DATE = "releaseDate";

        public static final String COLUMN_BACKDROP_PATH = "backdropPath";

        public static final String COLUMN_VOTE_COUNT = "voteCount";

        public static final String COLUMN_FAVORITE = "favorite";
    }
}
