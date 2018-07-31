package com.doobs.moviebrowser.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.doobs.moviebrowser.utils.MovieBrowserConstants;

/**
 * Bean class to hold the individual movie data
 *
 * Created by mduby on 7/7/18.
 */
@Entity(tableName = MovieBrowserConstants.Database.TABLE_NAME_MOVIE)
public class MovieBean implements Parcelable {
    // instance variables
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "rating")
    private Double rating;

    @ColumnInfo(name = "popularity")
    private Double popularity;

//    @NonNull
//    @ColumnInfo(name = "title")
//    private String title;

    @ColumnInfo(name = "plot")
    private String plotSynopsis;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(rating);
        parcel.writeDouble(popularity);
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

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object object) {
        MovieBean other = (MovieBean)object;

        return this.id.equals(other.getId());
    }
}
