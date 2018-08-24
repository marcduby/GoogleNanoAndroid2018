package com.doobs.baking.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean to hold recipe information
 *
 * Created by mduby on 8/24/18.
 */

public class RecipeBean {
    // instance variables
    private Integer id;
    private String name;
    private List<IngredientBean> ingredientBeanList = new ArrayList<IngredientBean>();
    private List<RecipeStepBean> stepBeanList = new ArrayList<RecipeStepBean>();
    private Double servings;
    private String imagePath;

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
}
