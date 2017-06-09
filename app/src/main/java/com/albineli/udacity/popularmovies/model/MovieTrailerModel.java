package com.albineli.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieTrailerModel implements Parcelable {
    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    private MovieTrailerModel(Parcel in) {
        site = in.readString();
        size = in.readInt();
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<MovieTrailerModel> CREATOR = new Creator<MovieTrailerModel>() {
        @Override
        public MovieTrailerModel createFromParcel(Parcel in) {
            return new MovieTrailerModel(in);
        }

        @Override
        public MovieTrailerModel[] newArray(int size) {
            return new MovieTrailerModel[size];
        }
    };

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "(site = " + site + ", size = " + size + ", key = " + key + ", name = " + name + ")";
    }
}
