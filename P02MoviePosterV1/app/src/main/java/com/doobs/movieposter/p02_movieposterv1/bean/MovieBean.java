package com.doobs.movieposter.p02_movieposterv1.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean class to hold the individual movie data
 *
 * Created by mduby on 7/7/18.
 */

public class MovieBean implements Parcelable {
    // instance variables
    private Integer id;
    private String name;            // also PK
    private Double rating;
    private Double popularity;
    private String title;
    private String plotSynopsis;
    private String releaseDate;
    private String imageUrl;

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(rating);
        parcel.writeDouble(popularity);
        parcel.writeString(title);
        parcel.writeString(plotSynopsis);
        parcel.writeString(releaseDate);
        parcel.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieBean> CREATOR = new Parcelable.Creator<MovieBean>() {
        public MovieBean createFromParcel(Parcel parcel) {
            MovieBean movieBean = new MovieBean();

            // set the data
            movieBean.setId(parcel.readInt());
            movieBean.setName(parcel.readString());
            movieBean.setRating(parcel.readDouble());
            movieBean.setPopularity(parcel.readDouble());
            movieBean.setTitle(parcel.readString());
            movieBean.setPlotSynopsis(parcel.readString());
            movieBean.setReleaseDate(parcel.readString());
            movieBean.setImageUrl(parcel.readString());

            // return
            return movieBean;
        }

        public MovieBean[] newArray(int size) {
            return new MovieBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
//        return "http://i.imgur.com/DvpvklR.png";
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
