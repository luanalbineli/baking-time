package com.albineli.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeIngredientModel implements Parcelable {
    @SerializedName("quantity")
    private float quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    protected RecipeIngredientModel(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<RecipeIngredientModel> CREATOR = new Creator<RecipeIngredientModel>() {
        @Override
        public RecipeIngredientModel createFromParcel(Parcel in) {
            return new RecipeIngredientModel(in);
        }

        @Override
        public RecipeIngredientModel[] newArray(int size) {
            return new RecipeIngredientModel[size];
        }
    };

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
