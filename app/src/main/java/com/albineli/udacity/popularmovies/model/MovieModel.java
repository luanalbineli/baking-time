package com.albineli.udacity.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.albineli.udacity.popularmovies.repository.data.MovieContract;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = MovieContract.MovieEntry.TABLE_NAME)
public class MovieModel implements Parcelable {

    private MovieModel(ContentValues contentValues) {
        if (contentValues.containsKey(MovieContract.MovieEntry._ID)) {
            id = contentValues.getAsInteger(MovieContract.MovieEntry._ID);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_POSTER_PATH)) {
            posterPath = contentValues.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_OVERVIEW)) {
            overview = contentValues.getAsString(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_TITLE)) {
            title = contentValues.getAsString(MovieContract.MovieEntry.COLUMN_TITLE);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)) {
            voteAverage = contentValues.getAsDouble(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)) {
            releaseDate = new Date(contentValues.getAsLong(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)) {
            backdropPath = contentValues.getAsString(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_VOTE_COUNT)) {
            voteCount = contentValues.getAsInteger(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
        }

        if (contentValues.containsKey(MovieContract.MovieEntry.COLUMN_FAVORITE)) {
            favorite = contentValues.getAsBoolean(MovieContract.MovieEntry.COLUMN_FAVORITE);
        }
    }

    public MovieModel() {

    }

    public MovieModel(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
        posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
        title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
        releaseDate = new Date(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
        backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
        voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT));
        favorite = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_FAVORITE)) == 1;
    }

    private MovieModel(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        title = in.readString();
        voteAverage = in.readDouble();
        releaseDate = new Date(in.readLong());
        backdropPath = in.readString();
        voteCount = in.readInt();
    }

    @PrimaryKey
    @ColumnInfo(index = true, name = MovieContract.MovieEntry._ID)
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_POSTER_PATH)
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_OVERVIEW)
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_TITLE)
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_RELEASE_DATE)
    @SerializedName("release_date")
    private Date releaseDate;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_VOTE_COUNT)
    @SerializedName("vote_count")
    private int voteCount;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_FAVORITE)
    private transient boolean favorite;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(title);

        parcel.writeDouble(voteAverage);
        parcel.writeLong(releaseDate.getTime());

        parcel.writeString(backdropPath);
        parcel.writeInt(voteCount);
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public static MovieModel fromContentValues(ContentValues contentValues) {
        return new MovieModel(contentValues);
    }

    public static MovieModel fromCursor(Cursor cursor) {
        return new MovieModel(cursor);
    }
}
