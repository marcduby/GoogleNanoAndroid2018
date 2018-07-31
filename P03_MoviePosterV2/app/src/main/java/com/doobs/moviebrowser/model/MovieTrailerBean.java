package com.doobs.moviebrowser.model;

/**
 * Bean class to hold movie trailer data
 *
 * Created by mduby on 7/30/18.
 */

public class MovieTrailerBean {
    // instance variables
    private String name;
    private String source;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
