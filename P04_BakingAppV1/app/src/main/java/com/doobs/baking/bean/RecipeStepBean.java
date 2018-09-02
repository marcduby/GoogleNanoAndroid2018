package com.doobs.baking.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.doobs.baking.util.BakingAppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class to hold recipe step
 *
 * Created by mduby on 8/24/18.
 */

public class RecipeStepBean implements Parcelable {
    // instance variables
    private Integer id = -1;
    private String description;
    private String shortDescription;
    private String videoUrl;
    private String thumbnailUrl;
    private String type = BakingAppConstants.RecipeStepType.STEP;
    private List<IngredientBean> ingredientBeanList = new ArrayList<IngredientBean>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thoumbnailUrl) {
        this.thumbnailUrl = thoumbnailUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<IngredientBean> getIngredientBeanList() {
        return ingredientBeanList;
    }

    public void setIngredientBeanList(List<IngredientBean> ingredientBeanList) {
        this.ingredientBeanList = ingredientBeanList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(shortDescription);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(this.type);

        // parcel the collection
        parcel.writeTypedList(ingredientBeanList);
    }

    public static final Parcelable.Creator<RecipeStepBean> CREATOR = new Parcelable.Creator<RecipeStepBean>() {
        public RecipeStepBean createFromParcel(Parcel parcel) {
            RecipeStepBean recipeStepBean = new RecipeStepBean();

            // set the data
            recipeStepBean.setId(parcel.readInt());
            recipeStepBean.setDescription(parcel.readString());
            recipeStepBean.setShortDescription(parcel.readString());
            recipeStepBean.setVideoUrl(parcel.readString());
            recipeStepBean.setThumbnailUrl(parcel.readString());
            recipeStepBean.setType(parcel.readString());

            // set the lists
            parcel.readTypedList(recipeStepBean.getIngredientBeanList(), IngredientBean.CREATOR);

            // return
            return recipeStepBean;
        }

        public RecipeStepBean[] newArray(int size) {
            return new RecipeStepBean[size];
        }
    };

}
