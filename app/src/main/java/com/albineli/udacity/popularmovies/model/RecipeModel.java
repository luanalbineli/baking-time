package com.albineli.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<RecipeIngredientModel> ingredientList;

    @SerializedName("steps")
    private List<RecipeStepModel> recipeStepList;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;

    protected RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createTypedArrayList(RecipeIngredientModel.CREATOR);
        recipeStepList = in.createTypedArrayList(RecipeStepModel.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeIngredientModel> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<RecipeIngredientModel> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<RecipeStepModel> getRecipeStepList() {
        return recipeStepList;
    }

    public void setRecipeStepList(List<RecipeStepModel> recipeStepList) {
        this.recipeStepList = recipeStepList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(recipeStepList);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
