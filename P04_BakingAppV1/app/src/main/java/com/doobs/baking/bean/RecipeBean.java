package com.doobs.baking.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean to hold recipe information
 *
 * Created by mduby on 8/24/18.
 */

public class RecipeBean implements Parcelable {
    // instance variables
    private Integer id;
    private String name;
    private Double servings;
    private String imagePath;
    private List<IngredientBean> ingredientBeanList = new ArrayList<IngredientBean>();
    private List<RecipeStepBean> stepBeanList = new ArrayList<RecipeStepBean>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientBean> getIngredientBeanList() {
        return ingredientBeanList;
    }

    public void setIngredientBeanList(List<IngredientBean> ingredientBeanList) {
        this.ingredientBeanList = ingredientBeanList;
    }

    public void addIngredientBean(IngredientBean ingredientBean) {
        this.ingredientBeanList.add(ingredientBean);
    }

    public List<RecipeStepBean> getStepBeanList() {
        return stepBeanList;
    }

    public void setStepBeanList(List<RecipeStepBean> stepBeanList) {
        this.stepBeanList = stepBeanList;
    }

    public void addStepBean(RecipeStepBean recipeStepBean) {
        this.stepBeanList.add(recipeStepBean);
    }

    public Double getServings() {
        return servings;
    }

    public void setServings(Double servings) {
        this.servings = servings;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(servings);
        parcel.writeString(imagePath);

        // parcel the collection
        parcel.writeList(ingredientBeanList);
        parcel.writeList(stepBeanList);
    }

    public static final Parcelable.Creator<RecipeBean> CREATOR = new Parcelable.Creator<RecipeBean>() {
        public RecipeBean createFromParcel(Parcel parcel) {
            RecipeBean recipeBean = new RecipeBean();

            // set the data
            recipeBean.setId(parcel.readInt());
            recipeBean.setName(parcel.readString());
            recipeBean.setServings(parcel.readDouble());
            recipeBean.setImagePath(parcel.readString());

            // set the lists
            parcel.readTypedList(recipeBean.getIngredientBeanList(), IngredientBean.CREATOR);
            parcel.readTypedList(recipeBean.getStepBeanList(), RecipeStepBean.CREATOR);

            // return
            return recipeBean;
        }

        public RecipeBean[] newArray(int size) {
            return new RecipeBean[size];
        }
    };

}
