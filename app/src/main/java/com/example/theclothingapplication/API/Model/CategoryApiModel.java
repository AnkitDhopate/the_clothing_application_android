package com.example.theclothingapplication.API.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CategoryApiModel implements Parcelable {
    private String name, categoryImage, slug;
    private ArrayList<CategoryApiModel> childrenCategory;

    // Category List Model
    public CategoryApiModel(String name, String categoryImage, String slug) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.slug = slug;
    }

    public CategoryApiModel(String name, String categoryImage, ArrayList<CategoryApiModel> childrenCategory, String slug) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.childrenCategory = childrenCategory;
        this.slug = slug ;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    protected CategoryApiModel(Parcel in) {
        name = in.readString();
        categoryImage = in.readString();
        childrenCategory = in.createTypedArrayList(CategoryApiModel.CREATOR);
        slug = in.readString();
    }

    public static final Creator<CategoryApiModel> CREATOR = new Creator<CategoryApiModel>() {
        @Override
        public CategoryApiModel createFromParcel(Parcel in) {
            return new CategoryApiModel(in);
        }

        @Override
        public CategoryApiModel[] newArray(int size) {
            return new CategoryApiModel[size];
        }
    };

    public ArrayList<CategoryApiModel> getChildrenCategory() {
        return childrenCategory;
    }

    public void setChildrenCategory(ArrayList<CategoryApiModel> childrenCategory) {
        this.childrenCategory = childrenCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(categoryImage);
        parcel.writeTypedList(childrenCategory);
        parcel.writeString(slug);
    }
}