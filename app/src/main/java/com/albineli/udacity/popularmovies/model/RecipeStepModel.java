package com.albineli.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeStepModel implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String descripton;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    protected RecipeStepModel(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        descripton = in.readString();
        videoURL = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<RecipeStepModel> CREATOR = new Creator<RecipeStepModel>() {
        @Override
        public RecipeStepModel createFromParcel(Parcel in) {
            return new RecipeStepModel(in);
        }

        @Override
        public RecipeStepModel[] newArray(int size) {
            return new RecipeStepModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(descripton);
        dest.writeString(videoURL);
        dest.writeString(thumbnailUrl);
    }
}
