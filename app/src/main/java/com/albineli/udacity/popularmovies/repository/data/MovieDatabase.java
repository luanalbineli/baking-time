package com.albineli.udacity.popularmovies.repository.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.albineli.udacity.popularmovies.model.MovieModel;

@Database(entities = {MovieModel.class}, version = 1)
@TypeConverters({RoomConverters.class})
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "movies";
    private static MovieDatabase mInstance;

    @SuppressWarnings("WeakerAccess")
    public abstract MovieDAO movieDAO();

    public static synchronized  MovieDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME).build();
        }

        return mInstance;
    }
}
