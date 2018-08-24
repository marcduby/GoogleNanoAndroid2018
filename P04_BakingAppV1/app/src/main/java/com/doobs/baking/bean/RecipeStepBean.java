package com.doobs.baking.bean;

/**
 * Bean class to hold recipe step
 *
 * Created by mduby on 8/24/18.
 */

public class RecipeStepBean {
    // instance variables
    private Integer id;
    private String description;
    private String shortDescription;
    private String videoUrl;
    private String thoumbnailUrl;

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

    public String getThoumbnailUrl() {
        return thoumbnailUrl;
    }

    public void setThoumbnailUrl(String thoumbnailUrl) {
        this.thoumbnailUrl = thoumbnailUrl;
    }
}
