package com.albineli.udacity.popularmovies.repository.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.albineli.udacity.popularmovies.model.MovieModel;

@Dao
public interface MovieDAO {
    @Insert
    long insert(MovieModel movieModel);

    @Query("SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME + " WHERE " + MovieContract.MovieEntry._ID + " = :id")
    Cursor selectById(int id);

    @Query("DELETE FROM " + MovieContract.MovieEntry.TABLE_NAME + " WHERE " + MovieContract.MovieEntry._ID + " = :id")
    int deleteById(int id);
}
