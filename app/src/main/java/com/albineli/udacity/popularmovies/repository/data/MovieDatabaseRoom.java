package com.albineli.udacity.popularmovies.repository.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.albineli.udacity.popularmovies.model.MovieModel;

@Database(entities = {MovieModel.class}, version = 1)
@TypeConverters({RoomConverters.class})
public abstract class MovieDatabaseRoom extends RoomDatabase {
    private static final String DATABASE_NAME = "movies";
    private static MovieDatabaseRoom mInstance;

    @SuppressWarnings("WeakerAccess")
    public abstract MovieDAO movieDAO();

    public static synchronized MovieDatabaseRoom getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabaseRoom.class, DATABASE_NAME).build();
        }

        return mInstance;
    }
}
